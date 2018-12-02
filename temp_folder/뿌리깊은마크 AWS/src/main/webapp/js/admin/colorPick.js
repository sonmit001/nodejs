(function( $ ) {

    $.fn.colorPick = function(config) {

        return this.each(function() {
            new $.colorPick(this, config || {});
        });

    };

    $.colorPick = function (element, options) {
        options = options || {};
        this.options = $.extend({}, $.fn.colorPick.defaults, options);
        if(options.str) {
            this.options.str = $.extend({}, $.fn.colorpickr.defaults.str, options.str);
        }
        $.fn.colorPick.defaults = this.options;
        this.color   = this.options.initialColor.toUpperCase();
        this.element = $(element);

        var dataInitialColor = this.element.data('initialcolor');
        if (dataInitialColor) {
            this.color = dataInitialColor;
            this.appendToStorage(this.color);
        }

        var uniquePalette = [];
        $.each($.fn.colorPick.defaults.palette.map(function(x){ return x.toUpperCase() }), function(i, el){
            if($.inArray(el, uniquePalette) === -1) uniquePalette.push(el);
        });

        this.palette = uniquePalette;

        return this.element.hasClass(this.options.pickrclass) ? this : this.init();
    };

    $.fn.colorPick.defaults = {
        'initialColor': '#000',
        'paletteLabel': 'Default palette:',
        'allowRecent': true,
        'recentMax': 5,
        'allowCustomColor': false,
        'palette': ["#1abc9c", "#E9967A", "#191970", "#3498db", "#9b59b6", "#ff4057", "#fd75b3", "#756acd",  
        			"#b43c5a", "#953295", "#A52A2A", "#ff6347", "#9370DB", "#7B68EE", "#D2B48C", "#00BFFF", 
        			"#556B2F", "#C71585", "#008080", "#F4A460", "#37a466", "#4169E1", "#D8BFD8", "#B0E0E6", 
        			"#8FBC8F", "#696969", "#228B22", "#342060", "#632162", "#353399", "#8fac39", "#3bb098",
        			"#913c30", "#d27b79", "#999433", "#216328", "#571d58", "#7daed4", "#d581a8", "#b43c4a",
        			"#40bdbf", "#4a862d", "#7e9933", "#7a6629", "#c78257", "#000"],
        'onColorSelected': function() {
            this.element.css({'backgroundColor': this.color, 'color': this.color});
        
        }
    };

    $.colorPick.prototype = {

        init : function(){

            var self = this;
            var o = this.options;
            
            $.proxy($.fn.colorPick.defaults.onColorSelected, this)();

            this.element.click(function(event) {
                event.preventDefault();
                self.show(event.pageX, event.pageY);
                var targetText = $(this).parent().children('span');

                $('.customColorHash').val(self.color);
                $('.colorPickButton').click(function(event) {
					self.color = $(event.target).attr('hexValue');
                    /*
                    if(self.color == "#000") {
                        targetText.css('color', '#fff');
                    }else {
                        targetText.css('color', self.color);
                    }
                    */
					self.appendToStorage($(event.target).attr('hexValue'));
					self.hide();
					$.proxy(self.options.onColorSelected, self)();
					return false;
            	});
                $('.customColorHash').click(function(event) {
                    return false;
                }).keyup(function (event) {
                    var hash = $(this).val();
                    if (hash.indexOf('#') !== 0) {
                        hash = "#"+hash;
                    }
                    if (/(^#[0-9A-F]{6}$)|(^#[0-9A-F]{3}$)/i.test(hash)) {
                        self.color = hash;
                        self.appendToStorage(hash);
                        $.proxy(self.options.onColorSelected, self)();
                        $(this).removeClass('error');
                    } else {
                        $(this).addClass('error');
                    }
                });

                return false;
            }).blur(function() {
                self.element.val(self.color);
                $.proxy(self.options.onColorSelected, self)();
                self.hide();
                return false;
            });

            $(document).on('click', function(event) {
                self.hide();
                return true;
            });

            return this;
        },

        appendToStorage: function(color) {
	        if ($.fn.colorPick.defaults.allowRecent === true) {
	        	var storedColors = JSON.parse(localStorage.getItem("colorPickRecentItems"));
				if (storedColors == null) {
		     	    storedColors = [];
	        	}
				if ($.inArray(color, storedColors) == -1) {
		    	    storedColors.unshift(color);
					storedColors = storedColors.slice(0, $.fn.colorPick.defaults.recentMax)
					localStorage.setItem("colorPickRecentItems", JSON.stringify(storedColors));
	        	}
	        }
        },

        show: function(left, top) {

            $("#colorPick").remove();

	        $("body").append('<div id="colorPick" style="display:none;top:' + top + 'px;left:' + left + 'px"><span>'+$.fn.colorPick.defaults.paletteLabel+'</span></div>');
	        jQuery.each(this.palette, function (index, item) {
				$("#colorPick").append('<div class="colorPickButton" hexValue="' + item + '" style="background:' + item + '"></div>');
			});
            if ($.fn.colorPick.defaults.allowCustomColor === true) {
                $("#colorPick").append('<input type="text" style="margin-top:5px" class="customColorHash" />');
            }
			if ($.fn.colorPick.defaults.allowRecent === true) {
				$("#colorPick").append('<span style="margin-top:5px">Recent:</span>');
				if (JSON.parse(localStorage.getItem("colorPickRecentItems")) == null || JSON.parse(localStorage.getItem("colorPickRecentItems")) == []) {
					$("#colorPick").append('<div class="colorPickButton colorPickDummy"></div>');
				} else {
					jQuery.each(JSON.parse(localStorage.getItem("colorPickRecentItems")), (index, item) => {
		        		$("#colorPick").append('<div class="colorPickButton" hexValue="' + item + '" style="background:' + item + '"></div>');
                        if (index == $.fn.colorPick.defaults.recentMax-1) {
                            return false;
                        }
					});
				}
			}
	        $("#colorPick").fadeIn(200);
	    },

	    hide: function() {
            
		    $( "#colorPick" ).fadeOut(200, function() {
			    $("#colorPick").remove();
			    return this;
			});
        },

    };

}( jQuery ));
