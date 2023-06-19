var showList = []

$(async () => {
    $('.preloader').fadeIn(100);
    
    await GetList()
    $('.preloader').fadeOut(1000);
})

$(".ticket-search-form").on('submit', (e) => e.preventDefault());

$('#search-val').on('input', function() {
    RenderShowList();
});


$("#show-new").click(async () => {
  
    const { value: formValues } = await Swal.fire({
        title: 'Thông tin suất chiếu',
        html:
        `
        <div class="form-group">
            <label for="shownew-cinemaid">Cinema ID</label>
            <input type="string" class="form-control" id="shownew-cinemaid">
        </div>
        <div class="form-group">
            <label for="shownew-movieid">Movie ID</label>
            <input type="string" class="form-control" id="shownew-movieid">
        </div>
        <div class="form-group">
            <label for="shownew-starttime">Start Time</label>
            <input type="string" class="form-control" id="shownew-starttime">
        </div>
        `
        ,
        focusConfirm: false,
        showCloseButton: true,
        preConfirm: () => {
            return {
                cinemaID : $('#shownew-cinemaid').val(),
                movieID : $('#shownew-movieid').val(),
                start_time : $('#shownew-starttime').val(),
            }
        }
    })
    
    if (!formValues) return 
    

    let response = await PenguRequestAPI( 'POST','api/show/add', 
    {
        "body" : formValues
    },
    { "Content-Type": "application/json"}, true) .then(r => r.json()).catch(error => {console.log(error); return false})

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


$("#modal-show-delete").click(async () => {
    let username = $("#show-modal-container #show-name").text()

    let result = await Swal.fire({
        title: 'Chắc chắn chứ?',
        text: "Hành động này sẽ không thể hoàn tác",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Vâng, tôi chắc chắn',
        cancelButtonText: 'Hủy bỏ',
    })

    if (!result.isConfirmed) 
        return;

    let response = await PenguRequestAPI( 'DELETE','api/show/delete?username=' + encodeURI(username), {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
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

$("#body-container").on("click", "tr #show-edit", async (e) => {
    let div = $(e.currentTarget).closest('tr')
    let id = div.find("#show-id").text()

    let show = await PenguRequestAPI( 'GET','api/show/' + encodeURIComponent(id), {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (!show) return;

    const { value: formValues } = await Swal.fire({
        title: 'Thông tin suất chiếu',
        html:
        `
        <div class="form-group">
            <label for="shownew-id">ID</label>
            <input type="string" class="form-control" id="shownew-id" value="${id}">
        </div>
        <div class="form-group">
            <label for="shownew-cinemaid">Cinema ID</label>
            <input type="string" class="form-control" id="shownew-cinemaid" value="${show.hallId}">
        </div>
        <div class="form-group">
            <label for="shownew-movieid">Movie ID</label>
            <input type="string" class="form-control" id="shownew-movieid" value="${show.movieId}">
        </div>
        <div class="form-group">
            <label for="shownew-starttime">Start Time</label>
            <input type="string" class="form-control" id="shownew-starttime" value="${show.startTime}">
        </div>
        `
        ,
        focusConfirm: false,
        showCloseButton: true,
        preConfirm: () => {
            return {
                cinemaID : $('#shownew-cinemaid').val(),
                movieID : $('#shownew-movieid').val(),
                start_time : $('#shownew-starttime').val(),
            }
        }
    })
    
    if (!formValues) return 
    

    let response = await PenguRequestAPI( 'PUT',`api/show/${encodeURIComponent(id)}/update`, 
    {
        "body" : formValues
    },
    { "Content-Type": "application/json"}, true) .then(r => r.json()).catch(error => {console.log(error); return false})

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
    let list = await PenguRequestAPI( 'GET','api/show/getall', {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (list)
        showList = list;

    RenderShowList()
}

function RenderShowList() {
    $('#show-modal-container').modal('hide')
    const container = $("#body-container")
    container.empty()

    let data = showList.filter((m) => m.movieName.indexOf($('#search-val').val()) != -1)

    for (let i = 0; i < data.length; i++) {
        let show = data[i]

        if (show) {
            let item = $(`
            <tr>
                <th scope="row" id="show-id"></th>
                <td id="show-name"></td>
                <td id="hall-name"></td>
                <td id="show-start"></td>
                <td id="show-end"></td>
                <td> <button type="button" class="btn btn-info" id="show-edit">Edit</button> </td>
            </tr>
            `)

            item.find("#show-id").text(show.showID)
            item.find("#show-name").text(show.hallName)
            item.find("#hall-name").text(show.movieName)
            item.find("#show-start").text(GetTimeFormat(new Date(show.startTime)))
            item.find("#show-end").text(GetTimeFormat(new Date(show.endTime)))
            container.append(item)
            
        }
    }
}


function GetTimeFormat(time) {
    return `${time.getHours().toString().padStart(2, '0')}:${time.getMinutes().toString().padStart(2, '0')} - ${time.getDate().toString().padStart(2, '0')}/${(time.getMonth() + 1).toString().padStart(2, '0')}`
}