// @ts-nocheck

function namee() {
  alert("I am Lucifer");
}

function x() {
  alert("x");
  $('.autoplay').slick({
    slidesToShow: 3,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2000,
  });
}

function y() {
  alert("yy");
  $('.slider').slick({
    autoplay: true,
    autoplaySpeed: 1500,
    arrows: true,
    prevArrow: '<button type="button" class="slick-prev"></button>',
    nextArrow: '<button type="button" class="slick-next"></button>',
    centerMode: true,
    slidesToShow: 3,
    slidesToScroll: 2
  });
}

function z() {
  alert("z");
  $(document).ready(function () {
    $('.single-item').slick({
      draggable: true,
      autoplay: true, /* this is the new line */
      autoplaySpeed: 2000,
      infinite: true,
      slidesToShow: 1,
      slidesToScroll: 1,
      touchThreshold: 1000,
    });
  });
}


$('.autoplay').slick({
  slidesToShow: 3,
  slidesToScroll: 1,
  autoplay: true,
  autoplaySpeed: 2000,
});

window.onload = function () {
  $('.slider').slick({
    autoplay: true,
    autoplaySpeed: 1500,
    arrows: true,
    prevArrow: '<button type="button" class="slick-prev"></button>',
    nextArrow: '<button type="button" class="slick-next"></button>',
    centerMode: true,
    slidesToShow: 3,
    slidesToScroll: 2
  });
};


var sliderr = $('.slider').slick({
  autoplay: true,
  autoplaySpeed: 1500,
  arrows: true,
  prevArrow: '<button type="button" class="slick-prev"></button>',
  nextArrow: '<button type="button" class="slick-next"></button>',
  centerMode: true,
  slidesToShow: 3,
  slidesToScroll: 2
});



