$(document).ready(function() {
	listar();

    $("#novoAluno").click(function() {
        window.location.href = 'cadaluno.jsp';
    });

    $("#pesquisaAluno").click(function() {
        $.ajax({
            url: 'AlunoController',
            type: 'GET',
            data: {
				acao: 'pesquisar',
                pesquisa: $('#pesquisa').val()
            }
        }).done(function (alunos) {
			renderTable(alunos);
        });
    });
});

function listar() {
	$.ajax({
		url: 'AlunoController',
		type: 'GET',
		data: {
			acao: 'listar'
		}
	}).done(function (alunos) {
		renderTable(alunos);
	});
}

function renderTable(alunos) {
	$('#info').hide();
	$('#listaAlunos').empty();

	if ($.isArray(alunos) && alunos.length > 0) {
		$('#resultado').show();

		$.each(alunos, function () {
			$('#listaAlunos').append(
				$('<tr>').append(
					$('<td>', { text: this.id }),
					$('<td>', { text: this.matricula }),
					$('<td>', { text: this.nome }),
					$('<td>', { text: this.curso }),
					$('<td>', { text: this.turma }),
					$('<td>').append(
						$('<div>', { class: 'btn-group btn-group-sm' }).append(
							$('<a>', { href: 'aluno.jsp?id=' + this.id, class: 'btn btn-primary', text: 'Editar ' }),
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
        title: "Você têm certeza que deseja excluir o aluno?",
        text: "Nenhum dado poderá ser recuperado, incluindo o histórico de locações.",
        type: "warning",
		showCancelButton: true,
		cancelButtonColor: '#DD6B55',
		confirmButtonText: 'Sim',
		cancelButtonText: 'Não'
    }).then(function (e) {
		if (e.value == true) {
			$.ajax({
				url: 'AlunoController',
				type: 'POST',
				data: {
					acao: 'excluir',
					id: id
				}
			}).done(function (response) {

				if (response == true) {
					swal('Sucesso', 'Livro excluído com sucesso!');
				} else {
					swal('Erro', 'Não foi possível excluir o livro... Contate o administrador.');
				}

				listar();
			});
		}
	});
}