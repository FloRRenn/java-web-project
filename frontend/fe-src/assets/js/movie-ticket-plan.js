$(async function() {
    $('.preloader').fadeIn(100);
    let movieId = getParam('movie-id')
    if (!movieId)
        return window.location.href = ('/movies')
    
    let shows = await PenguRequestAPI('GET','api/show/frommovie?id=' + encodeURI(movieId), {},  {},  true) .then(r => r.json()).catch(error => {console.log(error); return false})

    if (!shows)  
        return window.location.href = ('/movies')
    $(".seat-plan-wrapper").empty()


    let notShowExist = shows.length == 0

    let div = $(`
    <li>
        <div class="movie-name">
            <a href="/booking#0" class="name">Thời gian chiếu dự kiến</a>
            <div class="location-icon">
                <i class="fas fa-map-marker-alt"></i>
            </div>
        </div>
        <div class="movie-schedule">
        
        </div>
    </li> 
    `)

    for (let i = 0; i < shows.length; i++) {
        let show = shows[i]
        let startTime = new Date(show.startTime)
        div.find(".movie-schedule").append(`<div class="item" data-show-id="${formatContentHTML(show.showID)}">${GetTimeFormat(startTime)}</div`)
    }

    $(".seat-plan-wrapper").append(div)


    $('.preloader').fadeOut(1000);

    $('.seat-plan-wrapper').on("click", ".movie-schedule .item", function (e) {
        let showId = $(this).data("show-id")
        console.log(showId)

        return window.location.href = ('/seats?show-id=' + formatContentHTML(showId))
    });

    if (notShowExist) {
        await Swal.fire({
            icon: 'info',
            title: 'Thông báo',
            text: 'Hiện tại chưa có lịch chiếu cho bộ phim này!',
        })

        return window.location.href = ('/movies')
    }
    
})


function GetTimeFormat(time) {
    return `${time.getHours().toString().padStart(2, '0')}:${time.getMinutes().toString().padStart(2, '0')} - ${time.getDate().toString().padStart(2, '0')}/${(time.getMonth() + 1).toString().padStart(2, '0')}`
}