(function () {
    const overlay = document.getElementById("contatos-overlay");
    const modal = document.getElementById("contatos-modal");
    const titulo = document.getElementById("contatos-modal-titulo");
    const corpo = document.getElementById("contatos-modal-corpo");
    const fecharBtn = document.getElementById("contatos-modal-fechar");
    const editPanel = document.getElementById("contatos-modal-edit");
    const selectWrap = document.getElementById("contato-edit-select-wrap");
    const contatoSelect = document.getElementById("contato-edit-select");
    const tipoSelect = document.getElementById("contato-edit-tipo");
    const valorInput = document.getElementById("contato-edit-valor");
    const obsInput = document.getElementById("contato-edit-obs");
    const editMsg = document.getElementById("contato-edit-msg");
    const btnEditarContato = document.getElementById("btn-editar-contato");
    const btnExcluirCliente = document.getElementById("btn-excluir-cliente");
    const btnSalvarContato = document.getElementById("contato-edit-salvar");
    const btnCancelarEdit = document.getElementById("contato-edit-cancelar");

    let currentClienteId = null;
    let currentClienteNome = "";
    let currentContatos = [];
    let listaLoadSeq = 0;

    function escapeHtml(text) {
        if (text == null) return "";
        const div = document.createElement("div");
        div.textContent = text;
        return div.innerHTML;
    }

    function fecharModal() {
        if (overlay) overlay.classList.remove("active");
        if (modal) modal.classList.remove("active");
        esconderEdicao();
        currentClienteId = null;
        currentContatos = [];
    }

    function abrirModal() {
        if (overlay) overlay.classList.add("active");
        if (modal) modal.classList.add("active");
    }

    function esconderEdicao() {
        if (editPanel) editPanel.hidden = true;
        if (editMsg) {
            editMsg.hidden = true;
            editMsg.textContent = "";
        }
    }

    function mostrarMsgEdicao(texto, isErro) {
        if (!editMsg) return;
        editMsg.textContent = texto;
        editMsg.hidden = false;
        editMsg.classList.toggle("contatos-edit-msg--erro", Boolean(isErro));
    }

    function renderContatos(list) {
        if (!list.length) {
            return '<p class="contatos-vazio">Nenhum contato cadastrado para este cliente.</p>';
        }
        const itens = list
            .map((c) => {
                const obs = c.observacoes
                    ? `<span class="contato-obs">${escapeHtml(c.observacoes)}</span>`
                    : "";
                return `<li class="contato-item">
                    <span class="contato-tipo">${escapeHtml(c.tipoContato)}</span>
                    <span class="contato-valor">${escapeHtml(c.valorContato)}</span>
                    ${obs ? `<div class="contato-obs-wrap">${obs}</div>` : ""}
                </li>`;
            })
            .join("");
        return `<ul class="contatos-lista">${itens}</ul>`;
    }

    async function carregarContatosNaLista(clienteIdEsperado) {
        const idAlvo = clienteIdEsperado != null ? clienteIdEsperado : currentClienteId;
        if (idAlvo == null) return;
        const res = await fetch(
            "/api/clientes/" + encodeURIComponent(String(idAlvo)) + "/contatos"
        );
        if (currentClienteId !== idAlvo) return;
        if (!res.ok) {
            corpo.innerHTML =
                '<p class="contatos-erro">Não foi possível carregar os contatos.</p>';
            currentContatos = [];
            return;
        }
        const data = await res.json();
        if (currentClienteId !== idAlvo) return;
        currentContatos = data;
        corpo.innerHTML = renderContatos(currentContatos);
    }

    function preencherFormDoContato(contatoId) {
        const c = currentContatos.find((x) => Number(x.id) === Number(contatoId));
        if (!c) return;
        tipoSelect.value = c.tipoContato === "Email" ? "Email" : "Telefone";
        valorInput.value = c.valorContato || "";
        obsInput.value = c.observacoes || "";
    }

    function contatoIdSelecionado() {
        if (currentContatos.length === 1) return currentContatos[0].id;
        return parseInt(contatoSelect.value, 10);
    }

    function abrirPainelEdicao() {
        if (!currentContatos.length) {
            window.alert(
                "Não há contatos cadastrados para este cliente. Cadastre um contato no formulário de novo cliente."
            );
            return;
        }
        esconderEdicao();
        editPanel.hidden = false;
        selectWrap.hidden = currentContatos.length <= 1;
        contatoSelect.innerHTML = currentContatos
            .map(
                (c) =>
                    `<option value="${c.id}">${escapeHtml(c.tipoContato)} — ${escapeHtml(c.valorContato)}</option>`
            )
            .join("");
        contatoSelect.onchange = () => preencherFormDoContato(contatoIdSelecionado());
        if (currentContatos.length === 1) {
            preencherFormDoContato(currentContatos[0].id);
        } else {
            preencherFormDoContato(parseInt(contatoSelect.value, 10));
        }
    }

    document.querySelectorAll("tr.cliente-row").forEach((row) => {
        row.addEventListener("click", async () => {
            const id = row.getAttribute("data-cliente-id");
            const nome = row.getAttribute("data-cliente-nome") || "Cliente";
            if (!id) return;

            const idNum = parseInt(id, 10);
            const seq = ++listaLoadSeq;
            currentClienteId = idNum;
            currentClienteNome = nome;
            currentContatos = [];
            esconderEdicao();

            titulo.textContent = "Contatos — " + nome;
            corpo.innerHTML = '<p class="contatos-carregando">Carregando…</p>';
            abrirModal();

            try {
                await carregarContatosNaLista(idNum);
                if (seq !== listaLoadSeq) return;
            } catch {
                if (seq !== listaLoadSeq) return;
                corpo.innerHTML =
                    '<p class="contatos-erro">Erro de conexão. Tente novamente.</p>';
                currentContatos = [];
            }
        });
    });

    if (btnEditarContato) {
        btnEditarContato.addEventListener("click", (e) => {
            e.stopPropagation();
            if (currentClienteId == null) return;
            abrirPainelEdicao();
        });
    }

    if (btnExcluirCliente) {
        btnExcluirCliente.addEventListener("click", async (e) => {
            e.stopPropagation();
            if (currentClienteId == null) return;
            const ok = window.confirm(
                "Excluir o cliente \"" +
                    currentClienteNome +
                    "\"? Todos os contatos vinculados também serão removidos."
            );
            if (!ok) return;
            try {
                const res = await fetch(
                    "/api/clientes/" + encodeURIComponent(String(currentClienteId)),
                    { method: "DELETE" }
                );
                if (res.status === 204) {
                    window.location.reload();
                    return;
                }
                if (res.status === 404) {
                    window.alert("Cliente não encontrado.");
                    return;
                }
                window.alert("Não foi possível excluir o cliente.");
            } catch {
                window.alert("Erro de conexão. Tente novamente.");
            }
        });
    }

    if (btnCancelarEdit) {
        btnCancelarEdit.addEventListener("click", (e) => {
            e.stopPropagation();
            esconderEdicao();
        });
    }

    if (btnSalvarContato) {
        btnSalvarContato.addEventListener("click", async (e) => {
            e.stopPropagation();
            if (currentClienteId == null) return;
            const cid = contatoIdSelecionado();
            if (!cid) return;

            const tipo = tipoSelect.value;
            const valor = (valorInput.value || "").trim();
            const obsRaw = (obsInput.value || "").trim();

            if (!valor) {
                mostrarMsgEdicao("Preencha o valor do contato.", true);
                return;
            }

            mostrarMsgEdicao("Salvando…", false);
            editMsg.classList.remove("contatos-edit-msg--erro");

            try {
                const res = await fetch(
                    "/api/clientes/" +
                        encodeURIComponent(String(currentClienteId)) +
                        "/contatos/" +
                        encodeURIComponent(String(cid)),
                    {
                        method: "PUT",
                        headers: { "Content-Type": "application/json" },
                        body: JSON.stringify({
                            tipoContato: tipo,
                            valorContato: valor,
                            observacoes: obsRaw.length ? obsRaw : null,
                        }),
                    }
                );
                if (res.status === 400) {
                    mostrarMsgEdicao("Dados inválidos. Verifique tipo e valor.", true);
                    return;
                }
                if (!res.ok) {
                    mostrarMsgEdicao("Não foi possível salvar.", true);
                    return;
                }
                esconderEdicao();
                await carregarContatosNaLista(currentClienteId);
            } catch {
                mostrarMsgEdicao("Erro de conexão.", true);
            }
        });
    }

    if (fecharBtn) fecharBtn.addEventListener("click", fecharModal);
    if (overlay) overlay.addEventListener("click", fecharModal);

    document.addEventListener("keydown", (e) => {
        if (e.key === "Escape" && modal && modal.classList.contains("active")) {
            fecharModal();
        }
    });
})();
