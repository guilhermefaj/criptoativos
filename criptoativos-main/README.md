# Criptoativos - Plataforma de Investimento

## Visão Geral

Este projeto é uma plataforma de investimento em criptoativos que permite aos usuários:

- Registrar-se na plataforma.
- Fazer login e autenticar-se.
- Realizar transações de compra e venda de criptoativos.
- Gerar relatórios de transações, agrupados por ativo.
- Visualizar o saldo total de cada criptoativo adquirido ou vendido.
- Garantir a segurança das transações por meio de criptografia.

## Funcionalidades Principais

### 1. **Registro e Login de Usuários**

Os usuários podem registrar-se na plataforma fornecendo nome, email e senha. Após o registro, eles podem fazer login
para acessar suas transações e realizar novas operações.

### 2. **Transações de Compra e Venda**

Uma vez autenticado, o usuário pode realizar transações de compra e venda de criptoativos. Cada transação está associada
a um **ativo** (por exemplo, Bitcoin, Dogecoin) e contém informações como:

- Valor da transação
- Tipo de transação (Compra ou Venda)
- Data da transação

### 3. **Relatórios de Transações**

Os usuários podem gerar relatórios de suas transações. O relatório é gerado de forma agrupada por criptoativo,
mostrando:

- Todas as transações realizadas para cada ativo.
- O saldo total resultante das compras e vendas de cada ativo.

### 4. **br.com.voltz.service.Monitoramento e Gráficos**

A plataforma permite a geração de relatórios e gráficos que ajudam os usuários a monitorar suas transações ao longo do
tempo, fornecendo insights sobre o desempenho dos criptoativos.

### 5. **Segurança**

A segurança da plataforma é garantida através de criptografia avançada para proteger as transações e os dados dos
usuários. O sistema também autentica os acessos e monitora atividades suspeitas.

## Estrutura do Projeto

### Classes Principais

- **br.com.voltz.service.PlataformaWeb**:
    - Gerencia os usuários, suas transações e gera relatórios.
    - Métodos principais:
        - `registrarUsuario(br.com.voltz.model.Usuario usuario)`
        - `autenticarUsuario(String email, String senha)`
        - `exibirRelatorios(br.com.voltz.service.Monitoramento monitoramento)`
        - `gerarGraficoMonitoramento(br.com.voltz.service.Monitoramento monitoramento)`

- **br.com.voltz.model.Usuario**:
    - Representa um usuário da plataforma, contendo suas informações pessoais e transações.
    - Métodos principais:
        - `login()`
        - `logout()`
        - `adicionarTransacao(br.com.voltz.model.Transacao transacao)`
        - `getSaldoTotal()`

- **br.com.voltz.model.Transacao**:
    - Representa uma transação de compra ou venda de criptoativos.
    - Atributos:
        - `idTransacao`: Identificador único da transação.
        - `valor`: Valor da transação.
        - `tipo`: Tipo da transação (Compra ou Venda).
        - `ativo`: O criptoativo associado à transação.
    - Métodos principais:
        - `executarTransacao()`
        - `cancelarTransacao()`

- **br.com.voltz.model.Ativo**:
    - Representa um criptoativo (como Bitcoin, Dogecoin, etc.).
    - Atributos:
        - `idAtivo`: Identificador único do ativo.
        - `nome`: Nome do ativo.
        - `valorAtual`: Valor atual do ativo.
        - `volatilidade`: Volatilidade do ativo.
    - Métodos principais:
        - `getNome()`
        - `atualizarValor(double novoValor)`

- **br.com.voltz.service.Monitoramento**:
    - Gera relatórios e gráficos baseados nas transações do usuário.
    - Métodos principais:
        - `gerarRelatorioDiario()`
        - `gerarRelatorioUsuario(br.com.voltz.model.Usuario usuario)`

- **br.com.voltz.service.Seguranca**:
    - Garante a proteção das transações e autenticação dos usuários.
    - Métodos principais:
        - `criptografarDados()`
        - `autenticarAcesso()`
        - `monitorarAtividadesSuspeitas()`

## Fluxo de Operação

1. **Usuário se registra**: Um novo usuário fornece suas informações (nome, email e senha) e é registrado na plataforma.
2. **Usuário faz login**: O usuário fornece suas credenciais (email e senha) para fazer login e acessar a plataforma.
3. **Realização de transações**: Após o login, o usuário pode realizar transações de compra e venda de criptoativos.
   Cada transação é associada a um ativo específico e atualiza o saldo do usuário.
4. **Geração de relatórios**: O usuário pode solicitar relatórios detalhados que listam suas transações, separadas por
   criptoativo, além de visualizar o saldo total para cada ativo.
5. **br.com.voltz.service.Monitoramento**: A plataforma gera gráficos e relatórios periódicos para ajudar o usuário a monitorar suas
   transações e criptoativos ao longo do tempo.

## Como o Sistema Garante a Segurança

- **Criptografia**: Os dados do usuário e as transações são protegidos por criptografia avançada.
- **Autenticação de Acesso**: O sistema autentica cada acesso de usuário, verificando suas credenciais (email e senha).
- **br.com.voltz.service.Monitoramento de Atividades Suspeitas**: A classe `br.com.voltz.service.Seguranca` monitora tentativas de login mal-sucedidas e outras
  atividades suspeitas, garantindo a segurança da plataforma.

## Exemplo de Uso

1. O usuário Alice registra-se na plataforma fornecendo suas informações.
2. Alice faz login com seu email e senha.
3. Ela realiza a compra de 0,5 Bitcoins.
4. Alice realiza a venda de 100 Dogecoins.
5. Ela solicita um relatório de suas transações e visualiza o saldo total de cada criptoativo (Bitcoin e Dogecoin).
6. O sistema gera gráficos mostrando a evolução de suas transações.

