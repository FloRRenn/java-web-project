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


$("#genre-new").click(async () => {
  
    const { value: formValues } = await Swal.fire({
        title: 'Thông tin thể loại',
        html:
        `
        <div class="form-group">
            <label for="genrenew-name">Name</label>
            <input type="string" class="form-control" id="genrenew-name" placeholder="Name">
        </div>
        `
        ,
        focusConfirm: false,
        showCloseButton: true,
        preConfirm: () => {
            return {
                genre : $('#genrenew-name').val(),
            }
        }
    })
    
    if (!formValues) return 
    

    let response = await PenguRequestAPI( 'POST','api/genre/add', 
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


$("#body-container").on("click", "tr #genre-edit", async (e) => {
    let div = $(e.currentTarget).closest('tr')
    let id = div.find("#genre-id").text()
    let name = div.find("#genre-name").text()

    const { value: formValues } = await Swal.fire({
        title: 'Thông tin thể loại',
        html:
        `
        <div class="form-group">
            <label for="genrenew-name">Name</label>
            <input type="string" class="form-control" id="genrenew-name" placeholder="Name" value="${formatContentHTML(name)}">
        </div>
        `
        ,
        focusConfirm: false,
        showCloseButton: true,
        preConfirm: () => {
            return {
                genre : $('#genrenew-name').val(),
            }
        }
    })
    
    if (!formValues) return 
    

    let response = await PenguRequestAPI( 'PUT',`api/genre/${encodeURIComponent(id)}/update`, 
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
    let list = await PenguRequestAPI( 'GET','api/genre/getall', {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (list)
        genreList = list;

    RenderGenreList()
}

function RenderGenreList() {
    $('#user-modal-container').modal('hide')
    const container = $("#body-container")
    container.empty()

    let data = genreList.filter((m) => m.genre.indexOf($('#search-val').val()) != -1)

    for (let i = 0; i < data.length; i++) {
        let genre = data[i]

        if (genre) {
            let item = $(`
            <tr>
                <th scope="row" id="genre-id"></th>
                <td id="genre-name"></td>
                <td> 
                    <button type="button" class="btn btn-info" id="genre-edit">Edit</button> 
                </td>
            </tr>
            `)

            item.find("#genre-id").text(genre.id)
            item.find("#genre-name").text(genre.genre)
            item.find("#genre-modal").attr("onclick", `ShowModalUser('${formatContentHTML(genre.id)}');`)

            container.append(item)
            
        }
    }
}
