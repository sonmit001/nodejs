
/*URL 추가 1단계 모달*/
function showUrlForm() {
	$('.shareBox').fadeOut('fast', function() {
		$('.categoryBox').fadeOut('fast', function() {
			$('.urlBox').fadeIn('fast');
			$('.category_unshared-footer').fadeOut('fast', function() {
				$('.category_share-footer').fadeOut('fast', function() {
					$('.share-footer').fadeOut('fast', function() {
						$('.url-footer').fadeIn('fast')
					})
				})
			});
		});
		$('.modal-title').html('1단계');
	});
}
/* URL 추가 2단계 모달 */
function showCategoryForm() {
    $('.shareBox').fadeOut('fast', function() {
        $('.urlBox').fadeOut('fast', function() {
            $('.categoryBox').fadeIn('fast');
            $('.url-footer').fadeOut('fast', function() {
                $('.share-footer').fadeOut('fast', function() {
                    if (!$('#share').is(':checked')) {
                        $('.category_unshared-footer').fadeIn('fast')
                    } else {
                        $('.category_share-footer').fadeIn('fast')
                    }
                    
                    /*클릭, 더블클릭 구분을 위한 코드*/
                    var timer = 0;
                    var delay = 1000;
                    var prevent = false;
                    
                    $('#share').on("click", function() {
                        timer = setTimeout(function() {
                          if (!prevent) {
                                if ($('#share').is(':checked')) {
                                    $('.category_unshared-footer').fadeOut('fast', function() {
                                        $('.category_share-footer').fadeIn('fast')
                                    })
                                } else {
                                    $('.category_share-footer').fadeOut('fast', function() {
                                        $('.category_unshared-footer').fadeIn('fast')
                                    })
                                }
                          }
                          prevent = false;
                        }, delay);
                      })
                      .on("dblclick", function() {
                        clearTimeout(timer);
                        prevent = true;
                    });                            
                });
            });
        });
        $('.modal-title').html('2단계');
    });
}
/*URL 추가 3단계 모달*/
function showShareForm() {
	$('.categoryBox').fadeOut('fast', function() {
		$('.shareBox').fadeIn('fast');
		$('.category_share-footer').fadeOut('fast', function() {
			$('.share-footer').fadeIn('fast');
		})
		$('.modal-title').html('3단계')
	})
}

/* URL 추가 1단계 모달 바로열기 */
function openUrlModal() {
	showUrlForm();
	setTimeout(function() {
		$('#addBookmarkModal').modal('show');
	}, 230);
}

/*URL 추가 2단계 모달 바로열기*/
function openCategoryModal() {
  showCategoryForm();
  setTimeout(function() {
    $('#addBookmarkModal').modal('show');
  }, 230);
}
/*URL 추가 3단계 모달 바로열기*/
function openShareModal() {
  showShareForm();
  setTimeout(function() {
    $('#addBookmarkModal').modal('show');
  }, 230);
}
/*해시태그 생성*/
var hashtagList = [];
var hashtagStartPoint = 0;

function addHashtag() {
  if (event.keyCode == 13) {
    hashtagList.push($('#htag_input').val());
    var hashtag = $('#htag_input').val();
    console.log(hashtag);
    $('#htag_input').val('');
    $('#htag_input').focus();
    console.log(hashtagList);
    /*
    $('#htag_append').append("<input class='btn' type='button' value='"+$('#htag_input').val()+"'>");
    */
    $('#htag_append').append("<input class='btn btn-default btn-hash' id='btnHash" + hashtagStartPoint + "' type='button' value='#" + hashtag + "' onclick='deleteHashtag(this)'>");
    hashtagStartPoint++;
  }

}
/*해시태그 삭제*/
function deleteHashtag(data) {
  var str = $(data).attr('id');
  $('#' + str).remove();
  console.log($(data).val().replace("#", ""));
  var val = $(data).val().replace("#", "");
  hashtagList.splice($.inArray(val, hashtagList), 1);
  console.log(hashtagList);
}
/*폼 전송*/
function addBookmarkButton() {
  var hashStr = hashtagList.join();
  console.log(hashStr);
  $('#htag').val(hashStr);
  console.log($('#htag').val());

  //document.add.submit();
}
