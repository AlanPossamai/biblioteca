$(document).ready(function() {
	listar();

    $("#pesquisaUsuario").click(function() {
        $.ajax({
            url: 'UsuarioController',
            type: 'GET',
            data: {
				acao: 'pesquisar',
                pesquisa: $('#pesquisa').val()
            }
        }).done(function (usuarios) {
			renderTable(usuarios);
        });
    });
});

function listar() {
	$.ajax({
		url: 'UsuarioController',
		type: 'GET',
		data: {
			acao: 'listar'
		}
	}).done(function (usuarios) {
		renderTable(usuarios);
	});
}

function renderTable(usuarios) {
	console.log(usuarios);
	$('#info').hide();
	$('#listaUsuarios').empty();

	if ($.isArray(usuarios) && usuarios.length > 0) {
		$('#resultado').show();

		$.each(usuarios, function () {
			$('#listaUsuarios').append(
				$('<tr>').append(
					$('<td>', { text: this.id }),
					$('<td>', { text: this.nome }),
					$('<td>', { text: this.email }),
					$('<td>').append(
						$('<div>', { class: 'btn-group btn-group-sm' }).append(
							$('<a>', { href: 'usuario.jsp?id=' + this.id, class: 'btn btn-primary', text: 'Editar ' }),
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
        title: "Você têm certeza que deseja excluir o usuario?",
        text: "Nenhum dado poderá ser recuperado.",
        type: "warning",
		showCancelButton: true,
		cancelButtonColor: '#DD6B55',
		confirmButtonText: 'Sim',
		cancelButtonText: 'Não'
    }).then(function (e) {
		if (e.value == true) {
			$.ajax({
				url: 'UsuarioController',
				type: 'POST',
				data: {
					acao: 'excluir',
					id: id
				}
			}).done(function (response) {

				if (response == true) {
					swal('Sucesso', 'Usuario excluído com sucesso!');
				} else {
					swal('Erro', 'Não foi possível excluir o usuario... Contate o administrador.');
				}

				listar();
			});
		}
	});
}