var movieList = []
var movieTempList = []
var showLength = 12
var currentPage = 1
var genreList = {}

$(async () => {
    $('.preloader').fadeIn(100);
    let search = getParam('search')

    genreList = await PenguRequestAPI( 'GET','api/genre/getall', {},  {},  false) .then(r => r.json()).catch(error => {console.log(error); return false})
    
    $("#genre-container").empty()

    for (let i = 0; i < genreList.length; i++) {
        let item =$(`
        <div class="form-group"><input type="checkbox" name="genre" class="genre-item" id="genre${i}" data-index="${i}"><label for="genre${i}">${formatContentHTML(genreList[i].genre)}</label></div>`)
        $("#genre-container").append(item)
    }

    
    let list = await PenguRequestAPI( 'GET','api/movie/getall', {},  {},  false) .then(r => r.json()).catch(error => {console.log(error); return false})
    if (list)
        movieTempList = list;
        
    $('#search-val').val(search)
    $('#search-val').trigger('input')

    $('.preloader').fadeOut(1000);
})

$(".ticket-search-form").on('submit', (e) => e.preventDefault());

$('#search-val').on('input', function() {
    let val = formatVietnameseString($(this).val())

    movieList = movieTempList.filter((m) => formatVietnameseString(m.title).indexOf(val) != -1)
    RenderMovieList();
});

$('#show-length').change( function (e) {
    showLength = $(this).val() || 12;
    RenderMovieList();
});

$('#genre-container').on("click", ".genre-item", function (e) {
    let genreSelects = $(".genre-item:checked")

    let genreSelectsFilter = []
    for (let i = 0; i < genreSelects.length; i++) {
        genreSelectsFilter.push(genreList[genreSelects.eq(i).data("index")].genre)
    }

    if (genreSelects.length == 0) 
        movieList = movieTempList
    
    else 
        movieList = movieTempList.filter((m) => {
            for (let i = 0; i < genreSelectsFilter.length; i++) 
                if (m.genres.find(d => d.genre == genreSelectsFilter[i]) == undefined)
                    return false
   
            return true
        })
    
    RenderMovieList();
});

$("#switch-page-container").on("click", "a", (e) => {
    const select = $(e.currentTarget)
    const id = select.attr("id")
    const val = select.html()
    const maxPage = Math.ceil(movieList.length/showLength)
    
    switch (id) {
        case 'prev':
            if (currentPage > 1) {
                currentPage--;
                RenderMovieList();
            }
            break;
        case 'next':
            if (currentPage < maxPage) {
                currentPage++;
                RenderMovieList();
            }
            break;
        case 'page':
            currentPage = parseInt(val)
            RenderMovieList();
            break;
    }

})

function RenderMovieList() {
    const container = $("#movie-list-container")
    container.empty()

    let fromIndex = (currentPage - 1)*showLength
    let toIndex = currentPage*showLength    

    for (let i = fromIndex; i < toIndex; i++) {
        let movie = movieList[i]

        if (movie) {
            let item = $(`
            <div class="col-sm-6 col-lg-4">
                <div class="movie-grid">
                    <div class="movie-thumb c-thumb">
                        <a href="movie">
                            <img id='movie-img' src="assets/images/movie/movie01.jpg" alt="movie">
                        </a>
                    </div>
                    <div class="movie-content bg-one">
                        <h5 class="title m-0">
                            <a id="movie-title" href="movie">alone</a>
                        </h5>
                        <ul class="movie-rating-percent">
                            <span class="content" id="movie-info"></span>
                            <span class="content" id="movie-info-2"></span>
                        </ul>
                    </div>
                </div>
            </div>
            `)

            item.find('#movie-img').attr('src', movie.image)
            item.find('#movie-title').text(movie.title)
            item.find('#movie-info').text(`Thời lượng: ${GetTimeString(movie.durationInMins)}`)
            item.find('#movie-info-2').text(`Thể loại: ${movie.genres.map(d=> d.genre).join(", ")}`)
            item.find('a').attr('href', `movie?movie-id=${encodeURI(movie.id)}`)

            container.append(item)
            
        }
    }

    RenderSwitchPage()
}


function RenderSwitchPage() {
    const container = $("#switch-page-container")
    const maxPage = Math.ceil(movieList.length/showLength)
    const MAX_NUM_PAGE = 5

    let fromPage = -1
    let toPage = -1 

    if (currentPage <= MAX_NUM_PAGE) {
        fromPage = 1
        toPage = MAX_NUM_PAGE
    }
    else if (currentPage >= maxPage - MAX_NUM_PAGE) {
        fromPage = maxPage - MAX_NUM_PAGE
        toPage = maxPage
    }
    else {
        fromPage = currentPage - Math.floor(MAX_NUM_PAGE/2)
        toPage = currentPage + Math.floor(MAX_NUM_PAGE/2)
    }

    toPage = (toPage > maxPage) ? maxPage : toPage
    fromPage = (fromPage < 1) ? 1 : fromPage

    let pageList = ''

    for (let i = fromPage; i <= toPage; i++) {
        pageList += `\n <a id='page' class='${(currentPage == i) ? 'active' : ''}'>${i}</a>`
    }

    container.html(`
    <a id='prev'><i class="fas fa-angle-double-left"></i><span>Trang trước</span></a>
        ${pageList}
    <a id='next'><span>Trang sau</span><i class="fas fa-angle-double-right"></i></a>
    `)
}

