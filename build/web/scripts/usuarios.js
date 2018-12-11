var emailValido = false;
var senhaForte = false;
var nums;
$(function () {
    // Ações do cadastro de usuários
    $('#cancelar').click(function () {
        window.location.href = 'listusuario.jsp';
    });

    $('#salvar').click(function () {
        salvarUsuario();
    });

    $('#email').blur(function () {
		if ($('#id').val() <= 0) {
	        $.when(verificarEmailExistente()).done(function () {
				validarEmail();
			});
		} else {
			validarEmail();
		}
    });

    $('#nome').blur(function () {
        validarNomeUsuario();
    });

    $("#senha").blur(function () {
        validarSenhas();
        validarForcaSenha();
    }).complexify({
        minimumChars: 5,
        banMode: 'loose'
    }, function (valid, complexity) {
        var classe = '';
        if (complexity < 20) {
			$('#mtSenha').text('Muito fraca');
            classe = 'bg-danger';
        } else if (complexity < 42) {
			$('#mtSenha').text('Fraca');
			classe = 'bg-warning';
		} else if (complexity < 60) {
			$('#mtSenha').text('Satisfatória');
            classe = 'bg-info';
        } else if (complexity < 80) {
			$('#mtSenha').text('Forte');
			classe = 'bg-primary';
        } else {
			$('#mtSenha').text('Muito forte');
            classe = 'bg-success';
        }

        $('#mtSenha, #mtSenhaAux').css('width', complexity + '%').attr('aria-valuenow', complexity).removeClass('bg-danger bg-warning bg-info bg-success').addClass(classe);
        senhaForte = valid;
    });

    $("#confirmaSenha").blur(function () {
        validarSenhas();
    });

    // Ações da listagem de usuários
    $('#pesquisar').click(function () {
        pesquisar();
    });

	if ($('#resultado').length > 0) {
        pesquisar();
    } else {
		Captcha_AlanP.init('notARobot', 'captchaAsk', 'divAsk', 'divCaptcha', 'captchaConf', 'salvar', function() {
			toggleAviso('captchaConf', 'Ops... têm certeza?');
		}, function () {
			toggleAviso('captchaConf');
		});
	}
});

function verificarEmailExistente() {
    var def = $.Deferred();
    $.ajax({
        url: 'UsuarioController',
        data: {
            acao: 'verificarExistente',
            email: $('#email').val()
        }
    }).done(function (result) {
        if (result != "false") {
            toggleAviso('email', 'E-mail já cadastrado');
            emailValido = false;
        } else {
            toggleAviso('email');
            emailValido = true;
        }
        def.resolve();
    }).fail(function () {
        toggleAviso('email', 'Não foi possível verificar se e-mail já está cadastrado.');
        emailValido = false;
        def.resolve();
    });
    return def.promise();
}

function validarEmail() {
    var regex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var email = $('#email').val();
    if (regex.test(email)) {
        if (emailValido) {
            toggleAviso('email');
        }

        return true;
    } else {
        toggleAviso('email', 'E-mail inválido');
        return false;
    }
}

function validarNomeUsuario() {
    var nomeUsuario = $('#nome').val();
    if (nomeUsuario.length < 10) {
        toggleAviso('nome', 'Nome deve ter pelo menos 10 caracteres');
        return false;
    } else {
        toggleAviso('nome');
        return true;
    }
}

function validarForcaSenha() {
    if (senhaForte) {
        toggleAviso('senha');
    } else {
        toggleAviso('senha', 'Senha fraca');
    }
}

function validarSenhas() {
    var senha = $('#senha').val();
    var confirmaSenha = $('#confirmaSenha').val();
    if (senha.length == 0 && confirmaSenha.length == 0) {
        return false;
    }

    if (senha != confirmaSenha) {
        toggleAviso('senha', 'Senhas não conferem');
        toggleAviso('confirmaSenha', 'Senhas não conferem');
        return false;
    } else {
        toggleAviso('senha');
        toggleAviso('confirmaSenha');
        return true;
    }
}

function salvarUsuario() {
    $.when(verificarEmailExistente()).done(function () {
		if ($('#id').val() > 0) {
			emailValido = true;
		}

        validarForcaSenha();
        var confirmacaoSenha = validarSenhas();
        var usuarioValido = validarNomeUsuario();
        var formatoEmailValido = validarEmail();
        if (emailValido && formatoEmailValido && senhaForte && usuarioValido && confirmacaoSenha) {
            $.ajax({
                url: 'UsuarioController',
                type: 'POST',
                data: {
                    acao: 'salvar',
                    id: $('#id').val(),
                    nome: $('#nome').val(),
                    email: $('#email').val(),
                    senha: $('#senha').val(),
                    perfilUsuario: $('#perfilUsuario').val()
                }
            }).done(function (result) {
                if (result == 'OK') {
					swal("Sucesso!", "Usuário salvo com sucesso!", "success", {
						button: "Ok!"
					}).then((value) => {
						window.location.href = 'listusuario.jsp';
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
        } else {
            swal({
                type: 'error',
                title: 'Não é possível salvar',
                text: 'Você deve ajustar os campos marcados antes de salvar!'
            });
        }
    });
}

function pesquisar() {
    $.ajax({
        url: 'UsuarioController',
        type: 'GET',
        data: {
            pesquisa: $('#nome_pesquisa').val()
        }
    }).done(function (jsonUsuarios) {
		$('#resultado').empty();

        if ($.isArray(jsonUsuarios) && jsonUsuarios.length > 0) {
			$.each(jsonUsuarios, function () {
                $('#resultado').append(
                    $('<tr>').append(
                        $('<td>', { text: this.nome }),
                        $('<td>', { text: this.email }),
                        $('<td>').append(
                            $('<a>', { href: 'UsuarioController?acao=atualizar&id=' + this.id, class: 'text-dark', text: 'Alterar ' }).append(
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

function excluir(idUsuario) {
    swal({
        title: "Você têm certeza que deseja excluir o usuário?",
        text: "Após a exclusão, não será possível recuperar o usuário ou ter acesso à sua conta",
        type: "warning",
		showCancelButton: true,
		cancelButtonColor: '#DD6B55',
		confirmButtonText: 'Sim',
		cancelButtonText: 'Não'
    }).then(function (e) {
		if (e.value == true) {
			$.get('UsuarioController?acao=excluir&id=' + idUsuario, function( data ) {
				if (data == 'true') {
					swal('Sucesso', 'Usuário excluído com sucesso!');
				} else {
					swal('Erro', 'Não foi possível excluir o usuário...');
				}

				pesquisar();
			});
		}
	});
}

var Captcha_AlanP = function() {};
Captcha_AlanP.init = function(iniF, askF, capF1, capF2, confirm, bloq, errorFn, successFn) {
	Captcha_AlanP.updtFields(bloq, true);

	$('#' + iniF).click(function () {
		Captcha_AlanP.change(confirm, bloq, iniF, askF, capF1, capF2);
	});

	$('#' + confirm).on('change blur', function (e) {
		Captcha_AlanP.verify(confirm, $(e.target).val(), bloq, errorFn, successFn);
	});

};

Captcha_AlanP.change = function(confirm, bloq, iniF, askF, capF1, capF2) {
	nums = [
		Math.floor((Math.random() * 10) + 1),
		Math.floor((Math.random() * 10) + 1),
		Captcha_AlanP.getOperation()
	];

	$('#' + confirm).val('').removeClass('is-valid is-invalid');
	if ($('#' + iniF).prop('checked') === true) {
		$('#' + askF).val(nums[0] + String(' ' + (nums[2] === '*' ? 'x' : nums[2]) + ' ') + nums[1] + ' = ');
		$('#' + capF1 + ', #' + capF2).show();
	} else {
		Captcha_AlanP.updtFields(bloq, true);

		$('#' + askF).val('');
		$('#' + capF1 + ', #' + capF2).hide();
	}
};

Captcha_AlanP.verify = function(confirm, val, bloqField, errorFn, successFn) {
	var notVerified = (Number(val) !== eval(Number(nums[0]) + nums[2] + Number(nums[1])));
	if (notVerified === true) {
		$('#' + confirm).addClass('is-invalid');
	} else {
		$('#' + confirm).addClass('is-valid');
	}

	Captcha_AlanP.updtFields(bloqField, notVerified);

	if (typeof errorFn === 'function' && notVerified === true) {
		errorFn();
	} else if (typeof successFn === 'function') {
		successFn();
	}
};

Captcha_AlanP.updtFields = function(bloqField, bloq) {
	var DOMObj = document.getElementById(bloqField);
	var DOMObjType = DOMObj.tagName;

	switch (DOMObjType) {
		case 'BUTTON':
		case 'SELECT':
		case 'CHECKBOX':
			$(DOMObj).prop('disabled', bloq);
			break;

		case 'INPUT':
			$(DOMObj).prop('readonly', bloq);
			break;
	}
};

Captcha_AlanP.getOperation = function() {
    var ops = '*+-';
    var rnum = Math.floor(Math.random() * ops.length);
    return ops.substring(rnum, rnum + 1);
};