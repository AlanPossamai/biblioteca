var body = $('body');
$(document).on({
	ajaxStart: function () {
		body.addClass('loading');
	},
	ajaxStop: function () {
		body.removeClass('loading');
	}
});

function getFormData(form) {
    var formArray = $('#' + form).serializeArray();
    var formData = {};

    $.map(formArray, function (key, val) {
        formData[key['name']] = key['value'];
    });

    return formData;
}

function getUrlParameter(name) {
	name = name.replace(/[\[]/, '\\\[').replace(/[\]]/, '\\\]');
	var regexS = '[\\?&]' + name + '=([^&#]*)';
	var regex = new RegExp(regexS);
	var results = regex.exec(window.location.href);

	return ((!results || results[1] == null) ? '' : results[1]);
}

function toggleAviso(campo, msg) {
    if (msg) {
        $('#' + campo).addClass('is-invalid').next().html(msg).show();
    } else {
        $('#' + campo).removeClass('is-invalid').next().html('').hide();
    }
}

