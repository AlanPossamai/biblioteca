$(document).ready(function() {
	var id = getUrlParameter('id');
	if (id > 0) {
		$('#id').val(id);
		obter(id);
	}

    $('#salvar').click(function () {
		salvar();
    });
});

function obter(id) {
	$.ajax({
		url: 'CategoriaController',
		type: 'GET',
		data: {
			acao: 'obter',
			id: id
		}
	}).done(function (categoria) {
		if (categoria) {
			$.each(categoria, function(i, value) {
				if ($('#' + i).length > 0 && typeof value == 'string') {
					$('#' + i).val(value);
				}
			});
		} else {
			$('#info').show().children(':first-child').html("Não foi possível obter os dados.");
		}
	});
}

function salvar() {
	$.ajax({
		url: 'CategoriaController',
		type: 'POST',
		data: {
			acao: 'salvar',
			id: $('#id').val(),
			nome: $('#nome').val()
		}
	}).done(function (result) {
		if (result == true) {
			window.location.href = 'categorias.jsp';
		} else {
			$('#info').show().children(':first-child').html("Não foi possível salvar.");
		}
	});
}