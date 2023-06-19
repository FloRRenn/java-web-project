var genreList = []

$(async () => {
    $('.preloader').fadeIn(100);
    
    await GetList()
    $('.preloader').fadeOut(1000);
})

$(".ticket-search-form").on('submit', (e) => e.preventDefault());

$('#search-val').on('input', function() {
    RenderGenreList();
});


$("#hall-new").click(async () => {
  
    const { value: formValues } = await Swal.fire({
        title: 'Thông tin rạp',
        html:
        `
        <div class="form-group">
            <label for="hallnew-name">Name</label>
            <input type="string" class="form-control" id="hallnew-name" placeholder="Name">
        </div>
        <div class="form-group">
            <label for="hallnew-name">totalCol</label>
            <input type="number" class="form-control" id="hallnew-cols" placeholder="totalCol">
        </div>
        <div class="form-group">
            <label for="hallnew-name">totalRow</label>
            <input type="number" class="form-control" id="hallnew-rows" placeholder="totalRow">
        </div>
        `
        ,
        focusConfirm: false,
        showCloseButton: true,
        preConfirm: () => {
            return {
                name : $('#hallnew-name').val(),
                totalRow : $('#hallnew-rows').val(),
                totalCol : $('#hallnew-cols').val(),
            }
        }
    })
    
    if (!formValues) return 
    

    let response = await PenguRequestAPI( 'PUT','api/hall/new', 
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


$("#body-container").on("click", "tr #hall-delete", async (e) => {
    let div = $(e.currentTarget).closest('tr')
    let id = div.find("#hall-id").text()

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

    let response = await PenguRequestAPI( 'DELETE',`api/hall/${encodeURIComponent(id)}/delete`, {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
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


$("#body-container").on("click", "tr #hall-edit", async (e) => {
    let div = $(e.currentTarget).closest('tr')
    let id = div.find("#hall-id").text()
    let name = div.find("#hall-name").text()
    let totalCol = div.find("#hall-totalCol").text()
    let totalRow = div.find("#hall-totalRow").text()


    const { value: formValues } = await Swal.fire({
        title: 'Thông tin rạp',
        html:
        `
        <div class="form-group">
            <label for="hallnew-name">Name</label>
            <input type="string" class="form-control" id="hallnew-name" placeholder="Name" value="${formatContentHTML(name)}">
        </div>
        <div class="form-group">
            <label for="hallnew-name">totalCol</label>
            <input type="number" class="form-control" id="hallnew-cols" placeholder="totalCol" value="${formatContentHTML(totalCol)}">
        </div>
        <div class="form-group">
            <label for="hallnew-name">totalRow</label>
            <input type="number" class="form-control" id="hallnew-rows" placeholder="totalRow" value="${formatContentHTML(totalRow)}">
        </div>
        `
        ,
        focusConfirm: false,
        showCloseButton: true,
        preConfirm: () => {
            return {
                name : $('#hallnew-name').val(),
                totalRow : $('#hallnew-rows').val(),
                totalCol : $('#hallnew-cols').val(),
            }
        }
    })
    
    if (!formValues) return 
    

    let response = await PenguRequestAPI( 'PUT',`api/hall/${encodeURIComponent(id)}/edit`, 
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
    let list = await PenguRequestAPI( 'GET','api/hall/getall', {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (list)
        genreList = list;

    RenderGenreList()
}

function RenderGenreList() {
    $('#user-modal-container').modal('hide')
    const container = $("#body-container")
    container.empty()

    let data = genreList.filter((m) => m.name.indexOf($('#search-val').val()) != -1)

    for (let i = 0; i < data.length; i++) {
        let hall = data[i]

        if (hall) {
            let item = $(`
            <tr>
                <th scope="row" id="hall-id"></th>
                <td id="hall-name"></td>
                <td id="hall-totalCol"></td>
                <td id="hall-totalRow"></td>
                <td> 
                    <button type="button" class="btn btn-info col-4 mx-2" id="hall-edit">Edit</button> 
                    <button type="button" class="btn btn-danger col-4 mx-2" id="hall-delete">Delete</button>  
                </td>
            </tr>
            `)

            item.find("#hall-id").text(hall.id)
            item.find("#hall-name").text(hall.name)
            item.find("#hall-totalCol").text(hall.totalCol)
            item.find("#hall-totalRow").text(hall.totalRow)
            item.find("#hall-modal").attr("onclick", `ShowModalUser('${formatContentHTML(hall.id)}');`)

            container.append(item)
            
        }
    }
}
