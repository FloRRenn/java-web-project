(function ($) {
  "user strict";
  // Preloader Js
  $(window).on('load',async function () {
      $('.menu').html(`
      <li >
        <a href="/">Trang chủ</a>
      </li>
      <li>
        <a href="/movies">Đặt vé</a>
      </li>
      <li class="header-button pr-0" id="login-btn">
          <a href="/login">Đăng nhập</a>
      </li>
      `);
      let path =  window.location.pathname.split("/").at(-1)
      $(`.menu > li > a[href="${path}"]`).addClass('active')
      $('.footer-section').html(`
        <div class="container">
            <div class="footer-top">
                <div class="logo">
                    <a href="/">
                        <img src="assets/images/footer/footer-logo.png" alt="footer">
                    </a>
                </div>
            </div>
            <div class="footer-bottom">
                <div class="footer-bottom-area">
                  <div class="left">
                      <p>Copyright © 2023.All Rights Reserved By <a href="movie-list.html#0">PenguTicket </a></p>
                  </div>
                </div>
            </div>
        </div>
      `)

      if (await TokenIsVaild())  {
        $("#login-btn").find("a").attr("href", "/logout")
        $("#login-btn").find("a").text("Đăng xuất")
      }

      var img = $('.bg_img');
      img.css('background-image', function () {
        var bg = ('url(' + $(this).data('background') + ')');
        return bg;
      });
      // filter functions
      var $grid = $(".grid-area");
      var filterFns = {};
      $grid.isotope({
        itemSelector: '.grid-item',
        masonry: {
          columnWidth: 0,
        }
      });
      // bind filter button click
      $('ul.filter').on('click', 'li', function () {
        var filterValue = $(this).attr('data-filter');
        // use filterFn if matches value
        filterValue = filterFns[filterValue] || filterValue;
        $grid.isotope({
          filter: filterValue
        });
      });
      // change is-checked class on buttons
      $('ul.filter').each(function (i, buttonGroup) {
        var $buttonGroup = $(buttonGroup);
        $buttonGroup.on('click', 'li', function () {
          $buttonGroup.find('.active').removeClass('active');
          $(this).addClass('active');
        });
      });
  });
  $(document).ready(function () {
    // Nice Select
    $('.select-bar').niceSelect();
    // Lightcase 
    $('.video-popup').magnificPopup({
        type: 'iframe',
    });
    $("body").each(function () {
      $(this).find(".img-pop").magnificPopup({
          type: "image",
          gallery: {
              enabled: true
          }
        });
      });
    // Wow js active
    new WOW().init();
    //Faq
    $('.faq-wrapper .faq-title').on('click', function (e) {
      var element = $(this).parent('.faq-item');
      if (element.hasClass('open')) {
        element.removeClass('open');
        element.find('.faq-content').removeClass('open');
        element.find('.faq-content').slideUp(300, "swing");
      } else {
        element.addClass('open');
        element.children('.faq-content').slideDown(300, "swing");
        element.siblings('.faq-item').children('.faq-content').slideUp(300, "swing");
        element.siblings('.faq-item').removeClass('open');
        element.siblings('.faq-item').find('.faq-title, .faq-title-two').removeClass('open');
        element.siblings('.faq-item').find('.faq-content').slideUp(300, "swing");
      }
    });
    
    //MenuBar
    $('.header-bar').on('click', function () {
        $(".menu").toggleClass("active");
        $(".header-bar").toggleClass("active");
        $('.overlay').toggleClass('active');
    });
    $('.overlay').on('click', function () {
        $(".menu").removeClass("active");
        $(".header-bar").removeClass("active");
        $('.overlay').removeClass('active');
    });
    //Menu Dropdown Icon Adding
    $("ul>li>.submenu").parent("li").addClass("menu-item-has-children");
    // drop down menu width overflow problem fix
    $('ul').parent('li').hover(function () {
      var menu = $(this).find("ul");
      var menupos = $(menu).offset();
      if (menupos.left + menu.width() > $(window).width()) {
        var newpos = -$(menu).width();
        menu.css({
          left: newpos
        });
      }
    });
    $('.menu li a').on('click', function (e) {
      var element = $(this).parent('li');
      if (element.hasClass('open')) {
        element.removeClass('open');
        element.find('li').removeClass('open');
        element.find('ul').slideUp(300, "swing");
      } else {
        element.addClass('open');
        element.children('ul').slideDown(300, "swing");
        element.siblings('li').children('ul').slideUp(300, "swing");
        element.siblings('li').removeClass('open');
        element.siblings('li').find('li').removeClass('open');
        element.siblings('li').find('ul').slideUp(300, "swing");
      }
    })
    // Scroll To Top 
    var scrollTop = $(".scrollToTop");
    $(window).on('scroll', function () {
      if ($(this).scrollTop() < 500) {
        scrollTop.removeClass("active");
      } else {
        scrollTop.addClass("active");
      }
    });
    //Click event to scroll to top
    $('.scrollToTop').on('click', function () {
      $('html, body').animate({
        scrollTop: 0
      }, 500);
      return false;
    });
    // Header Sticky Here
    var headerOne = $(".header-section");
    $(window).on('scroll', function () {
      if ($(this).scrollTop() < 1) {
        headerOne.removeClass("header-active");
      } else {
        headerOne.addClass("header-active");
      }
    });
    $('.window-warning .lay').on('click', function() {
      $('.window-warning').addClass('inActive');
    })
    $('.seat-plan-wrapper li .movie-schedule .item').on('click', function() {
      $('.window-warning').removeClass('inActive');
    })
    //Tab Section
    $('.tab ul.tab-menu li').on('click', function (g) {
      var tab = $(this).closest('.tab'),
        index = $(this).closest('li').index();
      tab.find('li').siblings('li').removeClass('active');
      $(this).closest('li').addClass('active');
      tab.find('.tab-area').find('div.tab-item').not('div.tab-item:eq(' + index + ')').fadeOut(500);
      tab.find('.tab-area').find('div.tab-item:eq(' + index + ')').fadeIn(500);
      g.preventDefault();
    });
    $('.search-tab ul.tab-menu li').on('click', function (k) {
      var search_tab = $(this).closest('.search-tab'),
        searchIndex = $(this).closest('li').index();
        search_tab.find('li').siblings('li').removeClass('active');
      $(this).closest('li').addClass('active');
      search_tab.find('.tab-area').find('div.tab-item').not('div.tab-item:eq(' + searchIndex + ')').hide(10);
      search_tab.find('.tab-area').find('div.tab-item:eq(' + searchIndex + ')').show(10);
      k.preventDefault();
    });
    $('.tabTwo ul.tab-menu li').on('click', function (g) {
      var tabTwo = $(this).closest('.tabTwo'),
        index = $(this).closest('li').index();
      tabTwo.find('li').siblings('li').removeClass('active');
      $(this).closest('li').addClass('active');
      tabTwo.find('.tab-area').find('div.tab-item').not('div.tab-item:eq(' + index + ')').fadeOut(10);
      tabTwo.find('.tab-area').find('div.tab-item:eq(' + index + ')').fadeIn(10);
      g.preventDefault();
    });
    //Odometer
    $(".counter-item").each(function () {
      $(this).isInViewport(function (status) {
        if (status === "entered") {
          for (var i = 0; i < document.querySelectorAll(".odometer").length; i++) {
            var el = document.querySelectorAll('.odometer')[i];
            el.innerHTML = el.getAttribute("data-odometer-final");
          }
        }
      });
    });
    $('.social-icons li a').on('mouseover', function(e) {
      var social = $(this).parent('li');
      if(social.children('a').hasClass('active')) {
        social.siblings('li').children('a').removeClass('active');
        $(this).addClass('active');
      } else {
        social.siblings('li').children('a').removeClass('active');
        $(this).addClass('active');
      }
    });
    // $('.tab-slider').owlCarousel({
    //   loop:true,
    //   responsiveClass:true,
    //   nav:false,
    //   dots:false,
    //   margin: 30,
    //   autoplay:true,
    //   autoplayTimeout:2000,
    //   autoplayHoverPause:true,
    //   responsive:{
    //       0:{
    //           items:1,
    //       },
    //       576:{
    //           items:2,
    //       },
    //       768:{
    //           items:2,
    //       },
    //       992:{
    //           items:3,
    //       },
    //       1200:{
    //           items:4,
    //       }
    //   }
    // })
    $('.sponsor-slider').owlCarousel({
      loop: true,
      responsiveClass: true,
      nav: false,
      dots: false,
      margin: 30,
      autoplay: true,
      autoplayTimeout: 1500,
      autoplayHoverPause: true,
      responsive:{
          0:{
              items:1,
          },
          500:{
              items:2,
          },
          768:{
              items:3,
          },
          992:{
              items:4,
          },
          1200:{
              items:5,
          }
      }
    })
    $('.casting-slider').owlCarousel({
      loop:true,
      responsiveClass:true,
      nav:false,
      dots:false,
      margin: 100,
      autoplay:true,
      autoplayTimeout:2000,
      autoplayHoverPause:true,
      responsive:{
          0:{
              items:1,
          },
          450:{
              items:2,
          },
          768:{
              items:3,
          },
          992:{
              items:3,
          },
          1200:{
              items:4,
          }
      }
    });
    var owl = $('.casting-slider');
    owl.owlCarousel();
    // Go to the next item
    $('.cast-next').on('click', function() {
        owl.trigger('next.owl.carousel');
    })
    // Go to the previous item
    $('.cast-prev').on('click', function() {
        owl.trigger('prev.owl.carousel', [300]);
    })
    $('.casting-slider-two').owlCarousel({
      loop:true,
      responsiveClass:true,
      nav:false,
      dots:false,
      margin: 100,
      autoplay:true,
      autoplayTimeout:2000,
      autoplayHoverPause:true,
      responsive:{
          0:{
              items:1,
          },
          450:{
              items:2,
          },
          768:{
              items:3,
          },
          992:{
              items:3,
          },
          1200:{
              items:4,
          }
      }
    });
    var owlTT = $('.casting-slider-two');
    owlTT.owlCarousel();
    // Go to the next item
    $('.cast-next-2').on('click', function() {
        owlTT.trigger('next.owl.carousel');
    })
    // Go to the previous item
    $('.cast-prev-2').on('click', function() {
        owlTT.trigger('prev.owl.carousel', [300]);
    })
    $('.details-photos').owlCarousel({
      // loop:true,
      dots: false,
      autoplay: true,
      autoplayTimeout: 5000,
      smartSpeed: 1000,
      margin: 30,
      nav:false,
      responsive:{
          0:{
              items:1,                
          },
          576:{
              items:2
          },
          768:{
              items:3
          },
          1024:{
              items:3
          },
          1200:{
              items:3
          }
      }
    });
    // var book = 0;
    // $(".seat-free img").on('click', function(e) {
    //   if(book == 0) {
    //     $(this).attr("src","./assets/images/movie/seat01-free.png");
    //     book = 1;
    //   }
    //   else if(book == 1) {
    //     $(this).attr("src","./assets/images/movie/seat01-booked.png");
    //     book = 0;
    //   }
    // });
    // var bookTwo = 1;  
    // $(".seat-free-two img").on('click', function(e) {
    //   if(bookTwo == 0) {
    //     $(this).attr("src","./assets/images/movie/seat02-free.png");
    //     bookTwo = 1;
    //   }
    //   else if(bookTwo == 1) {
    //     $(this).attr("src","./assets/images/movie/seat02-booked.png");
    //     bookTwo = 0;
    //   }
    // });
    // shop cart + - start here
    var CartPlusMinus = $('.cart-plus-minus');
    CartPlusMinus.prepend('<div class="dec qtybutton">-</div>');
    CartPlusMinus.append('<div class="inc qtybutton">+</div>');
    $(".qtybutton").on("click", function() {
        var $button = $(this);
        var oldValue = $button.parent().find("input").val();
        if ($button.text() === "+") {
            var newVal = parseFloat(oldValue) + 1;
        } else {
            // Don't allow decrementing below zero
            if (oldValue > 0) {
                var newVal = parseFloat(oldValue) - 1;
            } else {
                newVal = 1;
            }
        }
        $button.parent().find("input").val(newVal);
    });
    //Speaker Slider
    $('.speaker-slider').owlCarousel({
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
              items:3,
          },
          992:{
              items:3,
          },
          1200:{
              items:4,
          }
      }
    });
    var owlT = $('.speaker-slider');
    owlT.owlCarousel();
    // Go to the next item
    $('.speaker-next').on('click', function() {
        owlT.trigger('next.owl.carousel');
    })
    // Go to the previous item
    $('.speaker-prev').on('click', function() {
        owlT.trigger('prev.owl.carousel', [300]);
    })
    //Client SLider
    $('.client-slider').owlCarousel({
      loop:true,
      nav:false,
      dots: true,
      items:1,
      autoplay:true,
      autoplayTimeout:2500,
      autoplayHoverPause:true,
    })
    //Count Down JAva Script
    $('.countdown').countdown({
      date: '11/15/2052 05:00:00',
      offset: +2,
      day: 'Day',
      days: 'Days'
    }, 
    function () {
        console.log('Done!');
    });
    //Widget Slider
    $('.widget-slider').owlCarousel({
      loop:true,
      nav:false,
      dots: false,
      items:1,
      autoplay:true,
      autoplayTimeout:2500,
      autoplayHoverPause:true,
      margin: 30,
    });
    var owlBela = $('.widget-slider');
    owlBela.owlCarousel();
    // Go to the next item
    $('.widget-next').on('click', function() {
        owlBela.trigger('next.owl.carousel');
    })
    // Go to the previous item
    $('.widget-prev').on('click', function() {
        owlBela.trigger('prev.owl.carousel', [300]);
    })
    $('.blog-slider').owlCarousel({
      loop:true,
      nav:false,
      dots: false,
      items:1,
      autoplay:true,
      autoplayTimeout:2500,
      autoplayHoverPause:true,
    });
    var owlB = $('.blog-slider');
    owlB.owlCarousel();
    // Go to the next item
    $('.blog-next').on('click', function() {
        owlB.trigger('next.owl.carousel');
    })
    // Go to the previous item
    $('.blog-prev').on('click', function() {
        owlB.trigger('prev.owl.carousel', [300]);
    })
  });
})(jQuery);

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

function getParam(param){
  return new URLSearchParams(window.location.search).get(param);
}


function GetTimeString(minute) {
  if (minute < 60) 
      return `${minute} phút`

  return `${Math.floor(minute/60)} giờ ${minute%60} phút`
}

function formatVietnameseString(str) {
  str = str.toLowerCase();
  str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
  str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
  str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
  str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
  str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
  str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
  str = str.replace(/đ/g, "d");
  return str;
}

function formatContentHTML(content) {
  return $("<p>").text(content).text()
}

groupBy = (a,f) => a.reduce( (x,c) => (x[f(c)]??=[]).push(c)&&x, {} )