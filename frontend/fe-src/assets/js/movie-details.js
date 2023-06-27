var movieId;
$(async function() {
    $('.preloader').fadeIn(100);
    movieId = getParam('movie-id')
    if (!movieId)
        return window.location.href = ('/')

    let movie = await PenguRequestAPI('GET','api/movie/' + encodeURI(movieId), {},  {},  false) .then(r => r.json()).catch(error => {console.log(error); return false})

    if (!movie)  
        return window.location.href = ('/')

    $("#movie-thumb").attr("src", movie.image)
    $("#movie-bg").css("background-image", `url(${movie.largeImage})`)
    $("#movie-title").text(movie.title)
    $("#movie-genre").text(movie.genres.map(d=> d.genre).join(", "))
    $("#movie-trailer").attr("href", movie.trailer)
    $("#movie-releaseDate").text(movie.releaseDate)
    $("#movie-duration").text(GetTimeString(movie.durationInMins))
    $("#movie-description").text(movie.description)
    $("#ticket-plan").attr("href", "/booking?movie-id=" + encodeURI(movieId))
    LoadComments();

    $('.preloader').fadeOut(1000);
})



$("#add-comment").on( "submit", async function( event ) {
    event.preventDefault();
    const form = $(this)

    let content = form.find('textarea[id="content"]').val().trim()
    if (!content) return
    
    //Request to Backend
    await PenguRequestAPI('POST', 'api/comment/add',
    {
        "body" : {
            "movie_id" : movieId,
            "comment": content,
            "rating": 5,
        }
    },
    { "Content-Type": "application/json"}, true).then(r => r.json()).catch(error => {console.log(error); return false})

    LoadComments();
});

async function LoadComments() {
    const container = $("#movie-review-container")
    container.empty();

    let res = await PenguRequestAPI('GET', 'api/comment/movie/' + encodeURI(movieId), {}, {}, false).then(r => r.json()).catch(error => {console.log(error); return false})

    for (let i = 0; i < res.length; i++) {
        let comment = res[i]

        if (comment) {
            let item = $(`
            <div class="movie-review-item">
                <div class="movie-review-content">
                    <div class="review" id="voting">
                       
                    </div>
                    <h6 class="cont-title" id="user">Awesome Movie</h6>
                    <p id="content">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer volutpat enim non ante egestas vehicula. Suspendisse potenti. Fusce malesuada fringilla lectus venenatis porttitor. </p>

                </div>
            </div>
            `)

            item.find('#user').text(comment.username)
            item.find('#content').text(comment.comment)
            item.find('#voting').html((`<i class="flaticon-favorite-heart-button"></i>`).repeat(comment.rated_starts))
            
            container.append(item)
            
        }
    }
}