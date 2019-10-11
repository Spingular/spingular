(function($, window, document, undefined) {
    'use strict';

    // init cubeportfolio
    $('#js-grid-slider-testimonials').cubeportfolio({
        layoutMode: 'slider',
        drag: true,
        auto: false,
        autoTimeout: 5000,
        autoPauseOnHover: true,
        showNavigation: true,
        showPagination: true,
        rewindNav: true,
        scrollByPage: false,
        gridAdjustment: 'responsive',
        mediaQueries: [{
            width: 0,
            cols: 1,
        }],
        gapHorizontal: 0,
        gapVertical: 0,
        caption: '',
        displayType: 'default',
    });
})(jQuery, window, document);