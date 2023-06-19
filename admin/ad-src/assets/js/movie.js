var movieList = []

$(async () => {
    $('.preloader').fadeIn(100);
    
    await GetList()
    $('.preloader').fadeOut(1000);
})

$(".ticket-search-form").on('submit', (e) => e.preventDefault());

$('#search-val').on('input', function() {
    RenderMovieList();
});


$("#movie-new").click(async () => {
  
    const { value: formValues } = await Swal.fire({
        title: 'Thông tin phim',
        html:
        `
        <div class="form-group">
            <label for="movienew-id">ID</label>
            <input type="string" class="form-control" id="movienew-id">
        </div>
        <div class="form-group">
            <label for="movienew-title">Title</label>
            <input type="string" class="form-control" id="movienew-title">
        </div>
        <div class="form-group">
            <label for="movienew-image">Image</label>
            <input type="string" class="form-control" id="movienew-image">
        </div>
        <div class="form-group">
            <label for="movienew-trailer">Trailer</label>
            <input type="string" class="form-control" id="movienew-trailer">
        </div>
        <div class="form-group">
            <label for="movienew-durationInMins">Duration</label>
            <input type="number" class="form-control" id="movienew-durationInMins">
        </div>
        <div class="form-group">
            <label for="movienew-country">Country</label>
            <input type="string" class="form-control" id="movienew-country">
        </div>
        <div class="form-group">
            <label for="movienew-language">Language</label>
            <input type="string" class="form-control" id="movienew-language">
        </div>
        <div class="form-group">
            <label for="movienew-actors">Actors</label>
            <input type="string" class="form-control" id="movienew-actors">
        </div>
        <div class="form-group">
            <label for="movienew-description">Description</label>
            <input type="string" class="form-control" id="movienew-description">
        </div>
        <div class="form-group">
            <label for="movienew-releaseDate">Release</label>
            <input type="string" class="form-control" id="movienew-releaseDate">
        </div>
        `
        ,
        focusConfirm: false,
        showCloseButton: true,
        preConfirm: () => {
            return {
                id : $('#movienew-id').val(),
                title : $('#movienew-title').val(),
                image : $('#movienew-image').val(),
                trailer : $('#movienew-trailer').val(),
                durationInMins : $('#movienew-durationInMins').val(),
                titcountryle : $('#movienew-country').val(),
                language : $('#movienew-language').val(),
                actors : $('#movienew-actors').val(),
                description : $('#movienew-description').val(),
                releaseDate : $('#movienew-releaseDate').val(),
            }
        }
    })
    
    if (!formValues) return 
    

    let response = await PenguRequestAPI( 'POST','api/movie/add', 
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


$("#modal-movie-delete").click(async () => {
    let username = $("#movie-modal-container #movie-name").text()

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

    let response = await PenguRequestAPI( 'DELETE','api/movie/delete?username=' + encodeURI(username), {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
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

$("#body-container").on("click", "tr #movie-edit", async (e) => {
    let div = $(e.currentTarget).closest('tr')
    let id = div.find("#movie-id").text()

    let movie = await PenguRequestAPI( 'GET','api/movie/' + encodeURIComponent(id), {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (!movie) return;


    const { value: formValues } = await Swal.fire({
        title: 'Thông tin phim',
        html:
        `
        <div class="form-group">
            <label for="movienew-id">ID</label>
            <input type="string" class="form-control" id="movienew-id" value="${formatContentHTML(movie.id)}">
        </div>
        <div class="form-group">
            <label for="movienew-title">Title</label>
            <input type="string" class="form-control" id="movienew-title" value="${formatContentHTML(movie.title)}">
        </div>
        <div class="form-group">
            <label for="movienew-image">Image</label>
            <input type="string" class="form-control" id="movienew-image" value="${formatContentHTML(movie.image)}">
        </div>
        <div class="form-group">
            <label for="movienew-trailer">Trailer</label>
            <input type="string" class="form-control" id="movienew-trailer" value="${formatContentHTML(movie.trailer)}">
        </div>
        <div class="form-group">
            <label for="movienew-durationInMins">Duration</label>
            <input type="number" class="form-control" id="movienew-durationInMins" value="${formatContentHTML(movie.durationInMins)}">
        </div>
        <div class="form-group">
            <label for="movienew-country">Country</label>
            <input type="string" class="form-control" id="movienew-country" value="${formatContentHTML(movie.country)}">
        </div>
        <div class="form-group">
            <label for="movienew-language">Language</label>
            <input type="string" class="form-control" id="movienew-language" value="${formatContentHTML(movie.language)}">
        </div>
        <div class="form-group">
            <label for="movienew-actors">Actors</label>
            <input type="string" class="form-control" id="movienew-actors" value="${formatContentHTML(movie.actors)}">
        </div>
        <div class="form-group">
            <label for="movienew-description">Description</label>
            <input type="string" class="form-control" id="movienew-description" value="${formatContentHTML(movie.description)}">
        </div>
        <div class="form-group">
            <label for="movienew-releaseDate">Release</label>
            <input type="string" class="form-control" id="movienew-releaseDate" value="${formatContentHTML(movie.releaseDate)}">
        </div>
        `
        ,
        focusConfirm: false,
        showCloseButton: true,
        preConfirm: () => {
            return {
                id : $('#movienew-id').val(),
                title : $('#movienew-title').val(),
                image : $('#movienew-image').val(),
                trailer : $('#movienew-trailer').val(),
                durationInMins : $('#movienew-durationInMins').val(),
                titcountryle : $('#movienew-country').val(),
                language : $('#movienew-language').val(),
                actors : $('#movienew-actors').val(),
                description : $('#movienew-description').val(),
                releaseDate : $('#movienew-releaseDate').val(),
            }
        }
    })

    
    if (!formValues) return 
    

    let response = await PenguRequestAPI( 'PUT',`api/movie/${encodeURIComponent(id)}/edit`, 
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
    let list = await PenguRequestAPI( 'GET','api/movie/getall', {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (list)
        movieList = list;

    RenderMovieList()
}

function RenderMovieList() {
    $('#movie-modal-container').modal('hide')
    const container = $("#body-container")
    container.empty()

    let data = movieList.filter((m) => m.title.indexOf($('#search-val').val()) != -1)

    for (let i = 0; i < data.length; i++) {
        let movie = data[i]

        if (movie) {
            let item = $(`
            <tr>
                <th scope="row" id="movie-id"></th>
                <td id="movie-name"></td>
                <td id="movie-genre"></td>
                <td> <button type="button" class="btn btn-info" id="movie-edit">Edit</button> </td>
            </tr>
            `)

            item.find("#movie-id").text(movie.id)
            item.find("#movie-name").text(movie.title)
            item.find("#movie-genre").text(movie.genres.map(d => d.genre.toString()).join(", "))

            container.append(item)
            
        }
    }
}
