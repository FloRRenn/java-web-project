$(async function() {
    $("#list-movies").empty()

    let list = await PenguRequestAPI( 'GET','api/movie/getall?pageSize=10', {},  {},  false) .then(r => r.json()).catch(error => {console.log(error); return false})

    console.log(list)

    list.forEach(element => {
        let item = $(
            `<div class="item">
                <div class="movie-grid">
                    <div class="movie-thumb c-thumb">
                        <a href="index-2.html#0">
                            <img id="movie-img" src="assets/images/movie/movie01.jpg" alt="movie">
                        </a>
                    </div>
                    <div class="movie-content bg-one">
                        <h5 class="title m-0"> 
                            <a id="movie-title" href="index-2.html#0">alone</a>
                        </h5>
                        <ul class="movie-rating-percent">
                            <span class="content" id="movie-info"></span>
                            <span class="content" id="movie-info-2"></span>
                        </ul>
                    </div>
                </div> 
            </div>`
        )
            
        item.find('#movie-img').attr('src', element.image)
        item.find('#movie-title').text(element.title)
        item.find('#movie-info').text(`Thời lượng: ${GetTimeString(element.durationInMins)}`)
        item.find('#movie-info-2').text(`Thể loại: ${element.genres.map(d=> d.genre).join(", ")}`)
        item.find('a').attr('href', `movie?movie-id=${encodeURI(element.id)}`)

        $("#list-movies").append(item)
    });
    

    $('.tab-slider').owlCarousel({
        loop:true,
        responsiveClass:true,
        nav:false,
        dots:false,
        margin: 30,
        autoplay:true,
        autoplayTimeout:2000,
        autoplayHoverPause:true,
        responsive:{
            0:{
                items:1,
            },
            576:{
                items:2,
            },
            768:{
                items:2,
            },
            992:{
                items:3,
            },
            1200:{
                items:4,
            }
        }
      })


      
    $('.preloader').fadeOut(1000);
})


$(".ticket-search-form").on( "submit", async function( event ) {
    event.preventDefault();
    const form = $(this)
    let value = form.find("#moive-name").val()

    window.location.href = ('/movies?search=' + encodeURI(value))
})