var vacinaExiste = false;

$(function () {
	$('#cancelar').click(function () {
        window.location.href = 'listvacina.jsp';
    });

	$('#formVacina').on('submit', function (e) {
		e.preventDefault();
        salvarVacina();
    });

	$('#nome').blur(function () {
		if ($('#id').val() <= 0) {
	       verificarVacinaExistente();
		}
    });

    $('#pesquisarVacina').click(function () {
        pesquisar();
    });

	if ($('#resultado').length > 0) {
		pesquisar();
	};
});


function salvarVacina() {
	var vacinaValida = validarVacina();
	if (vacinaValida === false) {
		swal({
			type: 'error',
			title: 'Não é possível salvar',
			text: 'Você deve ajustar os campos marcados antes de salvar!'
		});
	} else {
		$.ajax({
			url: 'VacinaController',
			type: 'POST',
			data: vacinaValida
		}).done(function (result) {
			if (result == 'OK') {
				swal("Sucesso!", "Vacina salva com sucesso!", "success", {
					button: "Ok!"
				}).then((value) => {
					window.location.href = 'listvacina.jsp';
				});
			} else {
				swal({
					title: "Ocorreu um problema...",
					text: result,
					icon: "warning",
					buttons: true
				});
			}
		});
	}
}

function validarVacina() {
	var valida = true;
	var vacina = {
		id: $('#id').val(),
		nome: $('#nome').val(),
		abreviatura: $('#abreviatura').val(),
		quantidade: $('#quantidade').val()
	};

	if ((vacina.nome.length < 5)) {
		toggleAviso('nome', 'Nome da vacina deve ter pelo menos 5 caracteres.');
		valida = false;
	} else if (vacinaExiste) {
		toggleAviso('nome', 'Vacina já cadastrada.');
		valida = false;
	}

	if (vacina.abreviatura.length < 3 || vacina.abreviatura.length > 7) {
		toggleAviso('abreviatura', 'A abreviatura deve possuir entre 3 e 7 caracteres.');
		valida = false;
	}

	if (vacina.quantidade < 0) {
		toggleAviso('quantidade', 'A quantidade não pode ser negativa.');
		valida = false;
	}

	return (valida === true ? vacina : false);
}

function verificarVacinaExistente() {
	var def = $.Deferred();
    $.ajax({
        url: 'VacinaController',
        data: {
            acao: 'verificarExistente',
            nome: $('#nome').val()
        }
    }).done(function (result) {
        if (result != 'false') {
            toggleAviso('nome', 'Vacina já cadastrada');
            vacinaExiste = true;
        } else {
            toggleAviso('nome');
            vacinaExiste = false;
        }
        def.resolve();
    }).fail(function () {
        toggleAviso('nome', 'Não foi possível verificar se a vacina já está cadastrada.');
        vacinaExiste = true;
        def.resolve();
    });

    return def.promise();
}

function pesquisar() {
    $.ajax({
        url: 'VacinaController',
        type: 'POST',
        data: {
            pesquisa: $('#pesquisaVacina').val()
        }
    }).done(function (jsonVacinas) {
		$('#resultado').empty();

        if ($.isArray(jsonVacinas) && jsonVacinas.length > 0) {
			$.each(jsonVacinas, function () {
                $('#resultado').append(
                    $('<tr>').append(
                        $('<td>', { text: this.nome }),
                        $('<td>', { text: this.abreviatura }),
						$('<td>', { text: this.quantidade }),
                        $('<td>').append(
                            $('<a>', { href: 'VacinaController?acao=atualizar&id=' + this.id, class: 'text-dark', text: 'Alterar ' }).append(
                                $('<i>', { class: 'fa fa-edit' })
                            ),
							$('<br>'),
                            $('<a>', { href: '#', class: 'text-dark', text: 'Excluir ', onClick: 'excluir(' + this.id + ')' }).append(
                                $('<i>', { class: 'fa fa-trash' })
                            )
                        )
                    )
                );
            });
        } else {
            $('#info').show().children(':first-child').html("Nenhum resultado foi retornado");
        }
    });
}

function excluir(idVacina) {
    swal({
        title: "Você têm certeza que deseja excluir a vacina?",
        text: "Após a exclusão, não será possível recuperar os dados da vacina.",
        type: "warning",
		showCancelButton: true,
		cancelButtonColor: '#DD6B55',
		confirmButtonText: 'Sim',
		cancelButtonText: 'Não'
    }).then(function (e) {
		if (e.value == true) {
			$.get('VacinaController?acao=excluir&id=' + idVacina, function( data ) {
				if (data == 'true') {
					swal('Sucesso', 'Vacina excluída com sucesso!');
				} else {
					swal('Erro', 'Não foi possível excluir a vacina...');
				}

				pesquisar();
			});
		}
	});
}

