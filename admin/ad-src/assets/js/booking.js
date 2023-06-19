var bookingList = []
var username 
$(async () => {

    let result = await Swal.fire({
        title: 'Mã đặt chỗ của người dùng',
        input: 'text',
        inputLabel: 'Username',
        inputPlaceholder: 'Username'
    })
      
   if (result)
        username = result.value

    $('.preloader').fadeIn(100);
    
    await GetList()
    $('.preloader').fadeOut(1000);
})

$(".ticket-search-form").on('submit', (e) => e.preventDefault());

$('#search-val').on('input', function() {
    RenderBookingList();
});




$("#body-container").on("click", "tr #booking-edit", async (e) => {
    let div = $(e.currentTarget).closest('tr')
    let id = div.find("#booking-id").text()

    let booking = await PenguRequestAPI( 'GET','api/booking/' + encodeURIComponent(id), {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (!booking) return;


    const { value: status } = await Swal.fire({
        title: 'Thông tin trạng thái',
        input: 'radio',
        inputOptions: {
            'PENDING': 'Đang xử lý',
            'BOOKED': 'Đặt thành công',
            'CANCLED': 'Hủy'
          },
        focusConfirm: false,
        showCloseButton: true
    })
    
    
    if (!status) return 

    let response = await PenguRequestAPI( 'PUT',`api/booking/user/${encodeURIComponent(username)}/${encodeURIComponent(id)}/setstatus?value=${encodeURIComponent(status)}`, {}, {}, true) .then(r => r.json()).catch(error => {console.log(error); return false})

    if (!response)
        return

    Swal.fire({
        position: 'top-end',
        text: response.message,
        backdrop : false,
        showConfirmButton: false,
        timer: 3000
      })

    await GetList()
})

async function GetList() {  
    if (!username) return
    
    let list = await PenguRequestAPI( 'GET',`api/booking/user/${encodeURIComponent(username)}/getall`, {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (list && list.message && list.message ==  "User is not found") {
        await Swal.fire({
            icon: 'error',
            title: 'Thông báo',
            text: 'Người dùng không tồn tại',
        })
        
        return location.reload();
    }


    if (list)
        bookingList = list;

    RenderBookingList()
}

function RenderBookingList() {
    $('#booking-modal-container').modal('hide')
    const container = $("#body-container")
    container.empty()

    let data = bookingList.filter((m) => m.id.indexOf($('#search-val').val()) != -1)

    for (let i = 0; i < data.length; i++) {
        let booking = data[i]

        if (booking) {
            let item = $(`
            <tr>
                <th scope="row" id="booking-id"></th>
                <td id="booking-movie"></td>
                <td id="booking-hall"></td>
                <td id="booking-show"></td>
                <td id="booking-seat"></td>
                <td id="booking-price"></td>
                <td id="booking-status"></td>
                <td id="booking-time"></td>
                <td> <button type="button" class="btn btn-info" id="booking-edit">Status</button> </td>
            </tr>
            `)

            item.find("#booking-id").text(booking.id)
            item.find("#booking-movie").text(booking.movieName)
            item.find("#booking-hall").text(booking.hallName)
            item.find("#booking-show").text(booking.showId)
            item.find("#booking-seat").text(booking.seats.join(", "))
            item.find("#booking-price").text(booking.price)
            item.find("#booking-status").text(booking.status)
            item.find("#booking-time").text(GetTimeFormat(new Date(booking.startTime)))
            container.append(item)
            
        }
    }
}


function GetTimeFormat(time) {
    return `${time.getHours().toString().padStart(2, '0')}:${time.getMinutes().toString().padStart(2, '0')} - ${time.getDate().toString().padStart(2, '0')}/${(time.getMonth() + 1).toString().padStart(2, '0')}`
}