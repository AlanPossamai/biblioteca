$(document).ready(function() {
	var id = getUrlParameter('id');
	if (id > 0) {
		$('#id').val(id);
		obter(id);
	} else {
		listarLivros();
		listarAlunos();
	}

    $('#salvar').click(function () {
		salvar();
    });
});

function obter(id) {
	$('#rowUpdate').show();

	$.ajax({
		url: 'EmprestimoController',
		type: 'GET',
		data: {
			acao: 'obter',
			id: id
		}
	}).done(function (emprestimo) {

		if (emprestimo) {
			$('#id').val(emprestimo.id);
			$('#dataDevolucao').val(parseData(emprestimo.dataDevolucao));
			$('#status').val(emprestimo.status);
			$('#estado').val(emprestimo.estado);
			$('#periodo').val(emprestimo.periodo);

			$('#idUnidadeLivro').append(
				$('<option>', { val: emprestimo.unidadeLivro.id, text: emprestimo.unidadeLivro.id.toString().padStart(13, '0') })
			).val(emprestimo.unidadeLivro.id).prop('disabled', true);

			$('#idAluno').append(
				$('<option>', { val: emprestimo.aluno.id, text: emprestimo.aluno.nome })
			).val(emprestimo.aluno.id).prop('disabled', true);
		} else {
			$('#info').show().children(':first-child').html("Não foi possível obter os dados.");
		}
	});
}

function salvar() {
	$.ajax({
		url: 'EmprestimoController',
		type: 'POST',
		data: {
			acao: 'salvar',
			id: $('#id').val(),
			idAluno: $('#idAluno').val(),
			idUnidadeLivro: $('#idUnidadeLivro').val(),
			status: $('#status').val(),
			periodo: $('#periodo').val(),
			estado: $('#estado').val(),
			dataDevolucao: $('#dataDevolucao').val()
		}
	}).done(function (result) {
		if (result == true) {
			window.location.href = 'emprestimos.jsp';
		} else {
			$('#info').show().children(':first-child').html("Não foi possível salvar.");
		}
	});
}

function listarAlunos() {
	$.ajax({
		url: 'AlunoController',
		type: 'GET',
		data: {
			acao: 'listar'
		}
	}).done(function (alunos) {
		$('#idAluno').empty();

		if (alunos && Array.isArray(alunos)) {
			$.each(alunos, function () {
				$('#idAluno').append(
					$('<option>', { val: this.id, text: this.nome })
				);
			});
		}
	});
}

function listarLivros() {
	$.ajax({
		url: 'LivroController',
		type: 'GET',
		data: {
			acao: 'obterunidades'
		}
	}).done(function (livros) {
		$('#idUnidadeLivro').empty();

		if (livros && Array.isArray(livros)) {
			$.each(livros, function () {
				$('#idUnidadeLivro').append(
					$('<option>', { val: this.id, text: this.id.toString().padStart(13, '0') })
				);
			});
		}
	});
}

function parseData(date) {
	return new Date(date).toLocaleDateString()
}