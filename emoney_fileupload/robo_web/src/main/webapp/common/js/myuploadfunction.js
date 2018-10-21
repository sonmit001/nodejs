$(function () {
	var url = "";
    $('#fileupload').fileupload({
        dataType: 'json',
        autoUpload: true,
        url :"insertFile.do",
        add: function (e, data) {
        	console.log(data);
        	data.submit();
        },
        done: function (e, data) {
        	var remove_icon = '<td><button type="button" value="test" class="btn btn-default filename_btn" onclick="';
        	remove_icon += 'delete_tr(';
        	remove_icon += "'" +data.result.org_name + "'";
        	remove_icon += ')"> <span class="glyphicon glyphicon-remove"></span></button></td>'
       		 $("#uploaded-files").append(
                 		$('<tr/>').attr("class",data.result.org_name)
                 		.append($('<td/>').text(data.result.org_name))
                 		.append(remove_icon)
         		);
            	 var fileinput = '<input type="text" name="attachfile" value="';
            	 fileinput += data.result.realname;
            	 fileinput += '" id="';
            	 fileinput += data.result.org_name;
            	 fileinput += '" >';
            	 
            	$('#sendwrite').append(
            			fileinput
            	)
        },
        fail: function(e, data) {
        	console.log(data.result);
        	  alert('Fail!');
        	},
           		
		dropZone: $('#dropzone')
    });
});