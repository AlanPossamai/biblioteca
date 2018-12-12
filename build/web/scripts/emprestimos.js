$(document).ready(function() {
	listar();

    $("#pesquisaAluno").click(function() {
		pesquisar('pesquisarAluno', $('#campoPesquisaAluno').val());
    });

	$("#pesquisaLivro").click(function() {
		pesquisar('pesquisarLivro', $('#campoPesquisaLivro').val());
    });

	$("#obterAtrasados").click(function() {
		obterAtrasados();
    });
});

function pesquisar(acao, pesquisa) {
	 $.ajax({
		url: 'EmprestimoController',
		type: 'GET',
		data: {
			acao: acao,
			pesquisa: pesquisa
		}
	}).done(function (emprestimos) {
		$('#info').hide();
		$('#listaEmprestimos').empty();
		$('#listaEmprestimos').html(emprestimos);
		$('#resultado').show();
	});
}

function obterAtrasados() {
	 $.ajax({
		url: 'EmprestimoController',
		type: 'GET',
		data: {
			acao: 'obterAtrasados'
		}
	}).done(function (emprestimos) {
		$('#info').hide();
		$('#listaEmprestimos').empty();
		$('#listaEmprestimos').html(emprestimos);
		$('#resultado').show();
	});
}

function listar() {
	$.ajax({
		url: 'EmprestimoController',
		type: 'GET',
		data: {
			acao: 'listar'
		}
	}).done(function (emprestimos) {
		$('#info').hide();
		$('#listaEmprestimos').empty();
		$('#listaEmprestimos').html(emprestimos);
		$('#resultado').show();
	});
}

function renderTable(emprestimos) {
	$('#info').hide();
	$('#listaEmprestimos').empty();

	if ($.isArray(emprestimos) && emprestimos.length > 0) {
		$('#resultado').show();

		$.each(emprestimos, function () {
			$('#listaEmprestimos').append(
				$('<tr>').append(
					$('<td>', { text: this.aluno.nome }),
					$('<td>', { text: this.livro.nome }),
					$('<td>', { html: getBadge(this.status) }),
					$('<td>').append(
						$('<div>', { class: 'btn-group btn-group-sm' }).append(
							$('<a>', { href: 'emprestimo.jsp?id=' + this.id, class: 'btn btn-primary', text: 'Editar ' }),
							$('<button>', { type: 'button', type: 'button', class: 'btn btn-danger', text: 'Excluir ', onClick: 'excluir(' + this.id + ')' })
						)
					)
				)
			);
		});
	} else {
		$('#resultado').hide();
		$('#info').show().children(':first-child').html("Nenhum resultado foi retornado");
	}
}

function excluir(id) {
    swal({
        title: "Você têm certeza que deseja excluir o emprestimo?",
        text: "O histórico será perdido",
        type: "warning",
		showCancelButton: true,
		cancelButtonColor: '#DD6B55',
		confirmButtonText: 'Sim',
		cancelButtonText: 'Não'
    }).then(function (e) {
		if (e.value == true) {
			$.ajax({
				url: 'EmprestimoController',
				type: 'POST',
				data: {
					acao: 'excluir',
					id: id
				}
			}).done(function (response) {

				if (response == true) {
					swal('Sucesso', 'Emprestimo excluído com sucesso!');
				} else {
					swal('Erro', 'Não foi possível excluir o emprestimo... Contate o administrador.');
				}

				listar();
			});
		}
	});
}

function getBadge(status) {
	if (status == 1) {
		return '<span class="badge badge-pill badge-success">Ativo</span>';
	} else {
		return '<span class="badge badge-pill badge-primary">Devolvido</span>';
	}
}