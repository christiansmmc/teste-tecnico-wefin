Contexto do teste técnico:

```
A Empresa X é uma instituição financeira inovadora e próspera, especializada na
concessão de empréstimos. Fundada há alguns anos por um grupo de empreendedores
experientes, a empresa rapidamente ganhou reconhecimento no mercado por sua
abordagem amigável e atendimento personalizado aos clientes. Seu objetivo é ajudar os
clientes a alcançarem seus objetivos financeiros de forma segura e responsável. A
empresa está em constante crescimento, portanto é bem importante pensar no crescimento
da regra de negócio.

Fluxo do Processo de Empréstimo para Empresa X:
1. Criar Pessoa:
    a. O candidato deve implementar as operações de CRUD para a entidade
"Pessoa" utilizando o Spring Boot. A tabela "Pessoa" deve conter os
campos: Nome, Identificador, DataNascimento, TipoIdentificador, Valor
mínimo mensal das parcelas do empréstimo e Valor máximo de todo o
empréstimo.
    b. O campo "TipoIdentificador" deve ser setado baseado no tamanho do
identificador recebido, Pessoa Física (PF) como 11 caracteres, Pessoa
Jurídica (PJ) como como 14 caracteres, Estudante Universitário
(EU)como como 8 caracteres e Aposentado (AP) como como 10
caracteres.
    c. Os valores mínimo e máximo do empréstimo, bem como o valor mínimo
das parcelas, são definidos automaticamente com base no
"TipoIdentificador".
    d. Pessoa Física (PF):
        i. Valor mínimo mensal das parcelas: R$ 300,00
        ii. Valor máximo de todo o empréstimo: R$ 10.000,00
    e. Pessoa Jurídica (PJ):
        i. Valor mínimo mensal das parcelas: R$ 1000,00
        ii. Valor máximo de todo o empréstimo: R$ 100.000,00
    f. Estudante Universitário (EU):
        i. Valor mínimo mensal das parcelas: R$ 100,00
        ii. Valor máximo de todo o empréstimo: R$ 10.000,00
    g. Aposentado (AP):
        i. Valor mínimo mensal das parcelas: R$ 400,00
        ii. Valor máximo de todo o empréstimo: R$ 25.000,00

2. Criar Tabela "Empréstimo":
    a. O candidato deve criar uma tabela "Empréstimo" no banco de dados para
registrar os empréstimos feitos por cada pessoa. Essa tabela deve conter
os campos necessários para armazenar as informações do empréstimo,
como valor do empréstimo, número de parcelas, status do pagamento, data
de criação, etc.
    b. A tabela "Empréstimo" possui uma relação com a tabela "Pessoa"
para vincular cada empréstimo à pessoa que o realizou.

3. Realizar Empréstimo (rota):
    a. O candidato deve criar uma rota específica para realizar um empréstimo.
Essa rota deve permitir que o usuário (pessoa) forneça o valor desejado
do empréstimo e o número de parcelas.
    b. O sistema deve verificar as seguintes condições:
        i. O identificador da pessoa é válido (existe no banco de dados).
    1. Validações dos Identificadores:
        a. CPF e CNPJ
            i. O candidato deve seguir as validações comuns
para CPF e CNPJ, verificando se os números
estão em um formato válido.
        b. Estudante Universitário (EU):
            i. O candidato deve validar que o número do
identificador possui exatamente 8
caracteres.
            ii. A soma do primeiro e último dígito deve ser
igual a 9.
        c. Aposentado (AP):
            i. O candidato deve validar que o número do
identificador possui exatamente 10
caracteres.
            ii. O último dígito não pode estar presente nos
outros 9 dígitos.
            ii. O empréstimo não ultrapassa o limite máximo estabelecido para
o tipo de identificador da pessoa.
            iii. O valor das parcelas não é inferior ao valor mínimo permitido para
o tipo de identificador da pessoa.
        iv. O número de parcelas não exceda 24.
        c. No fim do fluxo de empréstimo deve ser chamada uma outra api
para executar o pagamento.

4. Serviço de Pagamento:
    a. O candidato deve criar um serviço que realiza o pagamento de um
empréstimo. Esse serviço deve receber como entrada o ID do empréstimo
a ser pago.
    b. Ao executar o pagamento, o serviço deve alterar uma coluna na
tabela "Empréstimo" para marcar o empréstimo como "pago".

Com essa contextualização, o candidato terá um entendimento melhor do cenário em que a
Empresa X opera e poderá desenvolver o fluxo de empréstimo com maior contexto e
relevância.
```
## Documentação da API

#### Criar pessoa

```http
  POST /api/persons
```

| Campo   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `name` | `string` | **Obrigatório**. Nome da pessoa |
| `identifier` | `string` | **Obrigatório**. Documento da pessoa |
| `birthDate` | `localDate` | **Obrigatório**. Data de nascimento da pessoa |

#### Retorna todas pessoas

```http
  GET /api/persons
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | O ID da pessoa |
| `name`      | `string` | O nome da pessoa |
| `identifier`      | `string` | O documento da pessoa |
| `birthDate`      | `string` | A data de nascimento da pessoa |
| `identifierType`      | `string` | O tipo de documento da pessoa |
| `minInstallmentValue`      | `string` | O valor minimo de parcela da pessoa |
| `maxLoanValue`      | `string` | O valor máximo de empréstimo da pessoa |

#### Retorna uma pessoa

```http
  GET /api/persons/{id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | O ID da pessoa |
| `name`      | `string` | O nome da pessoa |
| `identifier`      | `string` | O documento da pessoa |
| `birthDate`      | `string` | A data de nascimento da pessoa |
| `identifierType`      | `string` | O tipo de documento da pessoa |
| `minInstallmentValue`      | `string` | O valor minimo de parcela da pessoa |
| `maxLoanValue`      | `string` | O valor máximo de empréstimo da pessoa |

#### Deleta uma pessoa

```http
  DELETE /api/persons/{id}
```

#### Atualiza uma pessoa

```http
  POST /api/persons/{id}
```

| Campo   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `name` | `string` | **Obrigatório**. Nome da pessoa |
| `identifier` | `string` | **Obrigatório**. Documento da pessoa |
| `birthDate` | `localDate` | **Obrigatório**. Data de nascimento da pessoa |

#### Criar empréstimo

```http
  POST /api/loans
```

| Campo   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `loanValue` | `bigDecimal` | **Obrigatório**. Valor empréstimo |
| `installmentsTotal` | `bigDecimal` | **Obrigatório**. Quantidade de parcelas |
| `person` | `object` | **Obrigatório**. Objeto da pessoa |
| `person.id` | `string` | **Obrigatório**. ID da pessoa que esta criando empréstimo |

## Stacks utilizada

- Java Spring Boot
- Mapstruct
    - Para simplificar a implementação de mapeamento entre objetos
- Datafaker
    - Para gerar campos randomizados nos testes
- Lombok
    - Para simplificar o desenvolvimento, reduzindo a verbosidade do código e automatizando tarefas comuns
- Spring Boot starter validation
    - Para simplificar a validação dos RequestBody