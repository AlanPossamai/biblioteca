<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>

<!-- Verifica as condições de acesso ao sistema -->
<!-- include_once './inc/acesso.inc.php'; -->
<!-- Cabeçalho da Página -->
<c:import url="inc/cabecalho.inc.jsp">
    <c:param name="pageName" value="Login" />
</c:import>

<!-- Conteúdo da Página -->
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading">
            <h2 class="panel-title text-center mt-5">Entrar</h2>
        </div>
        <div class="panel-body">

            <!-- Formulário que envia dados para o UsuarioController -->
            <form action="UsuarioController" method="post" class="form-horizontal mt-5">
                <fieldset>
                    <div class="row">
                        <div class="col-md-6 col-sm-8 col-xs-12 mx-auto">
                            <div class="form-group">
                                <label class="control-label" for="email">E-mail</label>
                                <input id="email" name="email" type="email" placeholder="Digite o seu e-mail" class="form-control" required maxlength="100" autocomplete="email">
                            </div>
                        </div>
					</div>
					<div class="row">
                        <div class="col-md-6 col-sm-8 col-xs-12 mx-auto">
                            <div class="form-group">
                                <label class="control-label" for="senha">Senha</label>
                                <input id="senha" name="senha" type="password" placeholder="Digite a sua senha" class="form-control" required maxlength="30" autocomplete="password">
                            </div>
                        </div>
                    </div>

                    <div id="mensagem-login" class="row mt-3 d-none">
                        <div class="col-md-6 col-sm-8 col-xs-12 mx-auto">
                            <div class="alert alert-warning alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                <strong>Falha ao logar: </strong> <span id="msg"></span>
                            </div>

                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6 col-sm-8 col-xs-12 mx-auto">
                            <label class="control-label" for="login"></label>
                            <button id="login" name="login" class="btn btn-primary btn-lg btn-block">Login</button>
                        </div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6 col-sm-8 col-xs-12 mx-auto">
							<label class="control-label" for="cancelar"></label>
							<button onClick="window.location = 'index.jsp'" id="cancelar" name="cancelar" class="btn btn-secondary btn-lg btn-block"  type="reset">Cancelar</button>
						</div>
					</div>
                </fieldset>
            </form>
        </div>
    </div>
</div>

<div class="wait"></div>

<script>
	function exibirMensagemLogin(msg) {
		$('#mensagem-login').removeClass('d-none');
		$('#msg').html(msg);
	}
</script>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<c:out value="${mensagem}" escapeXml="false"/>

</body>
</html>