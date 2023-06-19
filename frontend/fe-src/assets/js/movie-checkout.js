$(async function() {
    $('.preloader').fadeIn(100);
    let bookingId = getParam('booking-id')
    if (!bookingId)
        return window.location.href = ('/')

    let booking = await PenguRequestAPI('GET',`api/booking/${bookingId}`, {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (!booking)
        return window.location.href = ('/')

    if (booking.status != "PENDING") 
        return window.location.href = ('/')

    $(".back-button").attr("href", "/seats?show-id=" + encodeURI(booking.showId))
    
        
    $("#movie-name").text(booking.movieName)
    $("#movie-info").text(GetTimeFormat(new Date(booking.startTime)))
    $("#hall-name").text(booking.hallName)
    $("#seat-info").text(booking.seats.join(", "))
    $("#price").text(booking.price.toLocaleString('vi-VN', { style: 'currency', currency: 'VND', maximumFractionDigits: 9}))

    let countdown = 15*60
    setInterval(async () => {
        if (countdown > 0) {
            countdown--;
            $(".countdown").text(`${Math.floor(countdown/60).toString().padStart(2, '0')}:${(countdown%60).toString().padStart(2, '0')}`)
        }
        else if (!swal.isVisible()) {
            await Swal.fire({
                icon: 'error',
                title: 'Thông báo',
                text: 'Bạn đã hết thời gian thanh toán',
            })

            return window.location.href = ('/')
        }
    }, 1000)

    $('.preloader').fadeOut(1000);

   
    
    $(".payment-option li").click((e) => {
        let div = $(e.currentTarget)
        $(".payment-option li.active").removeClass("active")
        div.addClass("active")
    })

    $(".confirm").click(async (e) => {
        let div = $(".payment-option li.active")
        let type = div.data("payment")
        $('.preloader').fadeIn(100);
        console.log({
            "bookID" : bookingId,
            "paymentType" : type,
        }
            
        )
        let payment = await PenguRequestAPI('POST',`api/payment/create`,  {
            "body" : {
                "bookingID" : bookingId,
                "paymentType" : type,
            }
        },  { "Content-Type": "application/json" },  true).then(r => r.json()).catch(error => {console.log(error); return false})

        if (!payment)
            return window.location.href = ('/')
            
        if (!payment.paymentUrl) {
            await Swal.fire({
                icon: 'error',
                title: 'Thông báo',
                text: 'Có lỗi xảy ra vui lòng thử lại sau',
            })
            
            return window.location.href = ('/')
        }

        window.location.href = payment.paymentUrl;

    })
})


function GetTimeFormat(time) {
    return `${time.getHours().toString().padStart(2, '0')}:${time.getMinutes().toString().padStart(2, '0')} - ${time.getDate().toString().padStart(2, '0')}/${(time.getMonth() + 1).toString().padStart(2, '0')}`
}