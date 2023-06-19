$(async function() {
    $('.preloader').fadeIn(100);
    let showId = getParam('show-id')
    if (!showId)
        return window.location.href = ('/movies')

    let show = await PenguRequestAPI('GET',`api/show/${showId}`, {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})

    if (!show)  
        return window.location.href = ('/')
    // console.log(show)
    $(".title.movie").text(show.movieName)
    $(".hall").text("Pengu TPHCM - " + show.hallName)
    $(".date").text(GetTimeFormat(new Date(show.startTime)))
    $(".back-button").attr("href", "/booking?movie-id=" + encodeURI(show.movieId))
    
    let showSeats = await PenguRequestAPI('GET',`api/show/${showId}/seats`, {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    
    if (!showSeats)  
        return window.location.href = ('/')
    

    let seatsByRow = groupBy(showSeats, d => d.rowIndex)


    $(".seat-area").empty()
    
    for (let row in seatsByRow) {
        let rowSeats = seatsByRow[row]
        let div = $(`            
        <li class="seat-line">
            <span class='row-name'>f</span>
            <ul class="seat--area">
                <li class="front-seat">
                    <ul class="seats">
                      
                    </ul>
                </li>
            </ul>
            <span class='row-name'>f</span>
        </li>
    `)
        for (let i = 0; i < rowSeats.length; i++) {
            let seat = rowSeats[i]
            // console.log(seat)
            let colName = seat.colIndex + 1
            let rowName = seat.name.slice(0,-colName.toString().length).trim()
            let seatFree = seat.status == "AVAILABLE"
            // <li class="single-seat">
            //     <img src="assets/images/movie/seat01.png" alt="seat">
            // </li>
            // <li class="single-seat seat-free">
            //     <img src="assets/images/movie/seat01-free.png" alt="seat">
            //     <span class="sit-num">f7</span>
            // </li>

            div.find(".row-name").text(rowName)

            let item = $(`
            <li class="single-seat">
                <img src="assets/images/movie/seat01.png" alt="seat">
                <span class="sit-num">f7</span>
            </li>
            `)
            item.data('seat-id', seat.seatId)
            item.data('seat-price', seat.price)
            item.find(".sit-num").text(seat.name)
            if (seatFree) {
                item.addClass("seat-free")
                item.find("img").attr("src", "assets/images/movie/seat01-free.png")
            }
            else
                item.find(".sit-num").remove()

            div.find(".seats").append(item)
        }

        $(".seat-area").append(div)
    }

    let countdown = 5*60
    setInterval(async () => {
        if (countdown > 0) {
            countdown--;
            $(".countdown").text(`${Math.floor(countdown/60).toString().padStart(2, '0')}:${(countdown%60).toString().padStart(2, '0')}`)
        }
        else if (!swal.isVisible()) {
            await Swal.fire({
                icon: 'error',
                title: 'Thông báo',
                text: 'Bạn đã hết thời gian chọn ghế',
            })

            return window.location.href = ('/')
        }
    }, 1000)

    $('.preloader').fadeOut(1000);

    $('.seat-area').on("click", ".seat-free", function (e) {
        let div = $(this)
        let isBooked = div.hasClass("seat-booked")
        if (isBooked) 
            div.find("img").attr("src", "assets/images/movie/seat01-free.png")
        else
            div.find("img").attr("src", "assets/images/movie/seat01-booked.png")
     

        div.toggleClass("seat-booked", !isBooked)

        let booked = $(".seat-free.seat-booked")

        let bookedSeat = []
        let totalPrice = 0

        for (let i = 0; i < booked.length; i++) {
            let seat = booked.eq(i)
            bookedSeat.push(seat.find(".sit-num").text())
            totalPrice += seat.data('seat-price')
        }

        $(".seat-book-list").text(bookedSeat.join(", "))
        $(".seat-price").text(totalPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND', maximumFractionDigits: 9}))
    });

    $("#create").click(async () => {
        let booked = $(".seat-free.seat-booked")

        if (booked.length == 0) 
            return Swal.fire({
                icon: 'error',
                title: 'Thông báo',
                text: 'Vui lòng chọn ghế ngồi',
            })
            
        if (booked.length > 4) 
            return Swal.fire({
                icon: 'error',
                title: 'Thông báo',
                text: 'Mỗi lần bạn chỉ có thể đặt tối đa 4 ghế',
            })

        let seatIds = []

        
        for (let i = 0; i < booked.length; i++) {
            let seat = booked.eq(i)
            seatIds.push(seat.data("seat-id"))
        }

        // console.log(seatIds)

        let booking = await PenguRequestAPI('POST',`api/booking/add`, {
            "body" : {
                "show_id" : showId,
                "seat_ids" : seatIds
            }
        },  { "Content-Type": "application/json" },  true) .then(r => r.json()).catch(error => {console.log(error); return false})
        // console.log(booking)

        if (!booking || !booking.status || booking.status == "NOT_FOUND" || booking.status == "FORBIDDEN")  {
            await Swal.fire({
                icon: 'error',
                title: 'Thông báo',
                text: 'Có lỗi xảy ra: ' + booking.message,
            })
            
            return window.location.href =  $(".back-button").attr("href")
        }

        // status : "NOT_FOUND", "PENDING", "CONFLICT"
        if (booking.status == "CONFLICT") {
            await Swal.fire({
                icon: 'error',
                title: 'Thông báo',
                text: 'Ghế bạn chọn hiện đã không còn trống',
            })
            
            return location.reload();
        }


        if (booking.status != "PENDING") {
            await Swal.fire({
                icon: 'error',
                title: 'Thông báo',
                text: 'Có lỗi xảy ra vui lòng thử lại sau',
            })
            
            return location.reload();
        }

        return window.location.href = ('/checkout?booking-id=' + encodeURIComponent(booking.id))
    })

    $('.preloader').fadeOut(1000);
})


function GetTimeFormat(time) {
    return `${time.getHours().toString().padStart(2, '0')}:${time.getMinutes().toString().padStart(2, '0')} - ${time.getDate().toString().padStart(2, '0')}/${(time.getMonth() + 1).toString().padStart(2, '0')}`
}