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
		url: 'AlunoController',
		type: 'GET',
		data: {
			acao: 'obter',
			id: id
		}
	}).done(function (aluno) {
		if (aluno) {
			$.each(aluno, function(i, value) {
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
		url: 'AlunoController',
		type: 'POST',
		data: {
			acao: 'salvar',
			id: $('#id').val(),
			nome: $('#nome').val(),
			matricula: $('#matricula').val(),
			email: $('#email').val(),
			curso: $('#curso').val(),
			turma: $('#turma').val()
		}
	}).done(function (result) {
		if (result == true) {
			window.location.href = 'alunos.jsp';
		} else {
			$('#info').show().children(':first-child').html("Não foi possível salvar.");
		}
	});
}