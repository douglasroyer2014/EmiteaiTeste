import { useEffect, useState } from 'react';

function PersonComponent() {

    const person = {
        name: '',
        phone: '',
        cpf: '',
        address: '',
        number: '',
        complementary: '',
        cep: '',
        neighborhood: '',
        city: '',
        state: ''
    }

    const [objPerson, setObjPerson] = useState(person);

    const onChange = (e) => {
        setObjPerson({ ...objPerson, [e.target.name]: e.target.value })
    }

    const clean = () => {
        setObjPerson(person);
    }

    const removePerson = () => {
        if (!objPerson.cpf) {
            alert('CPF não foi informado');
            return;
        }
        fetch('http://localhost:8080/person/' + objPerson.cpf, {
            method: 'delete',
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json'
            }
        })
            .then(response => {
                return response.text();
            })
            .then(messageJson => {
                alert(messageJson);
            });
        clean();
    }

    const save = () => {
        if (!objPerson.cpf) {
            alert('CPF não foi informado');
            return;
        }
        fetch('http://localhost:8080/person', {
            method: 'post',
            body: JSON.stringify(objPerson),
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json'
            }
        })
            .then(response => {
                return response.text();
            })
            .then(messageJson => {
                alert(messageJson);
            });
        clean();
    }

    const update = () => {
        if (!objPerson.cpf) {
            alert('CPF não foi informado');
            return;
        }
        fetch('http://localhost:8080/person', {
            method: 'put',
            body: JSON.stringify(objPerson),
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json'
            }
        })
            .then(response => {
                return response.text();
            })
            .then(messageJson => {
                alert(messageJson);
            });
        clean();
    }

    const generateCSV = () => {
        fetch('http://localhost:8080/person/gerarCSV', {
            method: 'post',
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json'
            }
        })
            .then(response => {
                return response.text();
            })
            .then(messageJson => {
                alert(messageJson);
            });
    }

    const fillAddressField = () => {
        if (!objPerson.cep)
            return
        fetch('https://viacep.com.br/ws/'+ objPerson.cep.replace('-', '') +'/json/', {
            method: 'get',
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json'
            }
        })
            .then(response => response.json())
            .then(response => {
                let newPerson = {...objPerson};
                newPerson.address = response.logradouro;
                newPerson.neighborhood = response.bairro;
                newPerson.city = response.localidade;
                newPerson.state = response.estado;
                setObjPerson(newPerson);
            });
    }

    const fillFields = () => {
        if (!objPerson.cpf)
        return
        fetch('http://localhost:8080/person/' + objPerson.cpf, {
            method: 'get',
            headers: {
                'Content-type': 'application/json',
                'Accept': 'application/json'
            }
        })
            .then(response => response.json())
            .then(response => {
                let newPerson = {...objPerson};
                newPerson.name = response.name;
                newPerson.phone = response.phone;
                newPerson.number = response.number;
                newPerson.complementary = response.complementary;
                newPerson.address = response.address;
                newPerson.cep = response.cep;
                newPerson.neighborhood = response.neighborhood;
                newPerson.city = response.city;
                newPerson.state = response.state;
                setObjPerson(newPerson);
            });
    }

    return (
        <form>
            <input type='text' placeholder="Informe o CPF" className='form-control' value={objPerson.cpf} name='cpf' onChange={onChange} onBlur={fillFields} />
            <div className='row g-3'>
                <div className='col-md-6'>
                    <input type='text' placeholder="Informe o nome" className='form-control' value={objPerson.name} name='name' onChange={onChange} />
                </div>
                <div className='col-md-6'>
                    <input type='text' placeholder="Informe o telefone" className='form-control col-md-8' value={objPerson.phone} name='phone' onChange={onChange} />
                </div>
            </div>
            <input type='text' placeholder="Informe o CEP" className='form-control' value={objPerson.cep} name='cep' onChange={onChange} onBlur={fillAddressField} />
            <div className='row g-3'>
                <div className='col-md-6'>
                    <input type='text' placeholder="Informe o endereço" className='form-control' value={objPerson.address} name='address' onChange={onChange} />
                </div>
                <div className='col-md-6'>
                    <input type='text' placeholder="Informe o número" className='form-control' value={objPerson.number} name='number' onChange={onChange} />
                </div>
            </div>
            <div className='row g-3'>
                <div className='col-md-6'>
                    <input type='text' placeholder="Informe o complemento" className='form-control' value={objPerson.complementary} name='complementary' onChange={onChange} />
                </div>
                <div className='col-md-6'>
                    <input type='text' placeholder="Informe o bairro" className='form-control' value={objPerson.neighborhood} name='neighborhood' onChange={onChange} />
                </div>
            </div>
            <div className='row g-3'>
                <div className='col-md-6'>
                    <input type='text' placeholder="Informe o município" className='form-control' value={objPerson.city} name='city' onChange={onChange} />
                </div>
                <div className='col-md-6'>
                    <input type='text' placeholder="Informe o estado" className='form-control' value={objPerson.state} name='state' onChange={onChange} />
                </div>
            </div>
            <input type='button' value='Cadastrar' className='btn btn-success' onClick={save} />
            <input type='button' value='Alterar' className='btn btn-warning' onClick={update} />
            <input type='button' value='Remover' className='btn btn-danger' onClick={removePerson} />
            <input type='button' value='Gerar CSV' className='btn btn-info' onClick={generateCSV} />
            <input type='button' value='Cancelar' className='btn btn-secondary' onClick={clean} />
        </form>
    )
}

export default PersonComponent;