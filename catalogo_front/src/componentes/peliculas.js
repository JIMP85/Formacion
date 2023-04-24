import React, { Component, useState, useEffect } from "react";
import {
  ValidationMessage,
  ErrorMessage,
  Esperando,
  PaginacionCmd as Paginacion,
} from "../biblioteca/comunes";
import { titleCase } from "../biblioteca/formateadores";

export class PeliculasMnt extends Component {
    constructor(props) {
        super(props);
        this.state = {
        modo: "list",
        listado: null,
        elemento: null,
        error: null,
        loading: true,
        pagina: 0,
        paginas: 0,
        };
        this.idOriginal = null;
        this.url =
        (process.env.REACT_APP_API_URL || "http://localhost:8011/") +
        "api/peliculas/v1";
    }

    setError(msg) {
        this.setState({ error: msg, loading: false });
    }

    list(num) {
        let pagina = this.state.pagina;
        if (num || num === 0) pagina = num;
        this.setState({ loading: true });
        fetch(`${this.url}?sort=filmId&page=${pagina}&size=20`)
        .then((response) => {
            response.json().then(
            response.ok
                ? (data) => {
                    this.setState({
                    modo: "list",
                    listado: data.content,
                    loading: false,
                    pagina: data.number,
                    paginas: data.totalPages,
                    });
                }
                : (error) => this.setError(`${error.status}: ${error.error}`)
            );
        })
        .catch((error) => this.setError(error));
    }

    add() {
        this.setState({
        modo: "add",
        elemento: { filmId: 0, description: "",rating: "", releaseYear: 0, rentalDuration: 0, rentalRate: 0,
        replacementCost: 0 , actors: [], categories: [], Duracion: 0, Titulo: "P", Idioma: "", IdiomaVO: ""}
        });
    }

    edit(key) {
        this.setState({ loading: true });
        fetch(`${this.url}/${key}`)
        .then((response) => {
            response.json().then(
            response.ok
                ? (data) => {
                    this.setState({
                    modo: "edit",
                    elemento: data,
                    loading: false,
                    });
                    this.idOriginal = key;
                }
                : (error) => this.setError(`${error.status}: ${error.error}`)
            );
        })
        .catch((error) => this.setError(error));
    }

    view(key) {
        this.setState({ loading: true });
        fetch(`${this.url}/${key}`)
        .then((response) => {
            response.json().then(
            response.ok
                ? (data) => {
                    this.setState({
                    modo: "view",
                    elemento: data,
                    loading: false,
                    });
                }
                : (error) => this.setError(`${error.status}: ${error.error}`)
            );
        })
        .catch((error) => this.setError(error));
    }

    delete(key) {
        if (!window.confirm("¿Seguro?")) return;
        this.setState({ loading: true });
        fetch(`${this.url}/${key}`, { method: "DELETE" })
        .then((response) => {
            if (response.ok) this.list();
            else
            response.json().then((error) =>
                this.setError(`${error.status}:
            ${error.error}`)
            );
            this.setState({ loading: false });
        })
        .catch((error) => this.setError(error));
    }

    cancel() {
        this.list();
    }

    componentDidMount() {
        this.list(0);
    }

    send(elemento) {
        this.setState({ loading: true });
        // eslint-disable-next-line default-case
        switch (this.state.modo) {
        case "add":
            fetch(`${this.url}`, {
            method: "POST",
            body: JSON.stringify(elemento),
            headers: {
                "Content-Type": "application/json",
            },
            })
            .then((response) => {
                if (response.ok) this.cancel();
                else
                response.json().then((error) =>
                    this.setError(`${error.status}:
                            ${error.detail}`)
                );
                this.setState({ loading: false });
            })
            .catch((error) => this.setError(error));
            break;
        case "edit":
            fetch(`${this.url}/${this.idOriginal}`, {
            method: "PUT",
            body: JSON.stringify(elemento),
            headers: {
                "Content-Type": "application/json",
            },
            })
            .then((response) => {
                if (response.ok) this.cancel();
                else
                response.json().then((error) =>
                    this.setError(`${error.status}:
                                ${error.detail}`)
                );
                this.setState({ loading: false });
            })
            .catch((error) => this.setError(error));
            break;
        }
    }

    render() {
        if (this.state.loading) return <Esperando />;
        let result = [
          <ErrorMessage
            key="error"
            msg={this.state.error}
            onClear={() => this.setState({ error: null })}
          />,
        ];
        switch (this.state.modo) {
          case "add":
    
          case "edit":
            result.push(
              <PeliculasForm
                key="main"
                isAdd={this.state.modo === "add"}
                elemento={this.state.elemento}
                onCancel={(e) => this.cancel()}
                onDelete={(key) => this.delete(key)}
                onSend={(e) => this.send(e)}
              />
            );
            break;
    
          case "view":
            result.push(
              <PeliculasView
                key="main"
                elemento={this.state.elemento}
                onCancel={(e) => this.cancel()}
              />
            );
            break;
    
          default:
            if (this.state.listado)
              result.push(
                <PeliculasList
                  key="main"
                  listado={this.state.listado}
                  pagina={this.state.pagina}
                  paginas={this.state.paginas}
                  onAdd={(e) => this.add()}
                  onView={(key) => this.view(key)}
                  onEdit={(key) => this.edit(key)}
                  onChangePage={(num) => this.list(num)}
                />
              );
            break;
        }
        return result;
    }

}

function PeliculasList(props) {
    return (
      <>
        <table className="table table-hover table-striped">
          <thead className="table-info">
            <tr>
              <th>Lista de Peliculas</th>
              <th className="text-end">
                <input
                  type="button"
                  className="btn btnEdit"
                  value="Añadir"
                  onClick={(e) => props.onAdd()}
                />
              </th>
            </tr>
          </thead>
          <tbody className="table-group-divider">
            {props.listado.map((item) => (
              <tr key={item.filmId}>
                <td>{titleCase(item.title)}</td>
                <td className="text-end">
                  <div className="btn-group text-end" role="group">
                    <input
                      type="button"
                      className="btn btnView"
                      value="Ver"
                      onClick={(e) => props.onView(item.filmId)}
                    />
                    <input
                      type="button"
                      className="btn btnEdit"
                      value="Editar"
                      onClick={(e) => props.onEdit(item.filmId)}
                    />
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <Paginacion
          actual={props.pagina}
          total={props.paginas}
          onChange={(num) => props.onChangePage(num)}
        />
      </>
    );
}

function PeliculasView({ elemento, onCancel }) {
    return (
      <div>
        <p>
            <h1>{elemento.Titulo}</h1>
            <b>Código:</b> {elemento.filmId}
            <b>&nbsp; &nbsp; &nbsp; &nbsp;Idiomas: </b> {elemento.Idioma}
        </p>
        <p>
            <b>Clasificación: </b> {elemento.rating}
            <b>&nbsp; &nbsp; Duración: </b>{elemento.Duracion}
            <b>&nbsp; &nbsp; Fecha de estreno: </b> {elemento.releaseYear}
        </p> 
        <p>
            <b>Descripción:</b>
            <p>{elemento.description}</p>
        </p>
        <p>
            <b>Categorías:</b>
                {<ul>
                    {elemento.categories.map(categoria =>(
                        <li>{categoria}</li>
                    ))}
                </ul>}

            <b>Reparto:</b>
                {<ul>
                    {elemento.actors.map(actor =>(
                        <li>{actor}</li>
                    ))}
                </ul>}
        </p>
        <p>
            <b>Alquiler:</b>
            <br />
            <b>Duración del alquiler: </b> {elemento.rentalDuration} días
            <b>&nbsp; &nbsp; Precio del alquiler: </b>{elemento.rentalRate} €
            <b>&nbsp; &nbsp; Coste de reposión: </b> {elemento.replacementCost} €
        </p>

        <p>
          <button
            className="btn btn-primary"
            type="button"
            onClick={(e) => onCancel()}
          >
            Volver
          </button>
        </p>
      </div>
    );
}

function PeliculasForm(props) {
    const [elemento, setElemento] = useState(props.elemento)
    const [msgErr, setMsgErr] = useState([])
    const [invalid, setInvalid] = useState(false)
    const [categories, setCategories] = useState([])
    const [actores, setActores] = useState([])
    const [idiomas, setIdiomas] = useState([])
    const [clasificaciones, setClasificaciones] = useState([])
    const [idCategoria, setIdCategoria] = useState(null);
    const [idActor, setIdActor] = useState(null);
    let url = (process.env.REACT_APP_API_URL || 'http://localhost:8011/api/');
    const form = React.createRef();
    const onSend = () => { props.onSend && props.onSend(elemento); }
    const onCancel = () => {
        if (props.onCancel) props.onCancel();
    };

    const handleChange = (event) => {
        const cmp = event.target.name;
        const valor = event.target.value;
        elemento[cmp] = valor;
        setElemento(elemento)
        validar();
    }

    const validar = () => {
        if (form.current) {
            const errors = {};
            let invalid = false;
            setMsgErr(errors)
            setInvalid(invalid)
        }
    }
    const setError = (cmp, msg) => {
        msgErr[cmp] = msg
        setMsgErr(msgErr)
    }

    useEffect(() => validar(), [])

    useEffect(() => {
        fetch(`${url}peliculas/v1/clasificaciones`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    setClasificaciones(data)
                } : error => setError('rating', `${error.status}: ${error.error}`))
            })
            .catch(error => setError('rating', error.message))
    }, [])

    useEffect(() => {
        fetch(`${url}idiomas/v1`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    setIdiomas(data)
                } : error => setError('languageId', `${error.status}: ${error.error}`))
            })
            .catch(error => setError('languageId', error.message))
    }, [])

    useEffect(() => {
        fetch(`${url}categorias/v1`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    if (data.length > 0) setIdCategoria(data[0].id)
                    setCategories(data)
                } : error => setError('categories', `${error.status}: ${error.error}`))
            })
            .catch(error => setError('categories', error.message))
    }, [])

    useEffect(() => {
        fetch(`${url}actores/v1?sort=firstName`)
            .then(response => {
                response.json().then(response.ok ? data => {
                    if (data.length > 0) setIdActor(data[0].actorId)
                    setActores(data)
                } : error => setError('actors', `${error.status}: ${error.error}`))
            })
            .catch(error => setError('actors', error.message))
    }, [])

    return (
        <form ref={form} >
            <div className="form-group">
                <label htmlFor="title">Título</label>
                <input type="text" className="form-control"
                    id="title" name="title" value={elemento.Titulo}
                    onChange={handleChange} required minLength="1" maxLength="128" />
                <ValidationMessage msg={msgErr.title} />
            </div>
            <div className="form-group">
                <label htmlFor="description">Descripción</label>
                <textarea className="form-control"
                    id="description" name="description" value={elemento.description}
                    onChange={handleChange} minLength="2" />
                <ValidationMessage msg={msgErr.description} />
            </div>
            <div className="form-group">
                <label htmlFor="releaseYear">Año</label>
                <input type="number" className="form-control"
                    id="releaseYear" name="releaseYear" value={elemento.releaseYear}
                    onChange={handleChange} min={1895} max={(new Date()).getFullYear()} />
                <ValidationMessage msg={msgErr.releaseYear} />
            </div>
            <div className="form-group">
                <label htmlFor="length">Duración</label>
                <div className="input-group">
                    <input type="number" className="form-control"
                        id="length" name="length" value={elemento.Duracion}
                        onChange={handleChange} min={1} />
                    <span className="input-group-text">minutos</span>
                </div>
                <ValidationMessage msg={msgErr.length} />
            </div>
            <div className="form-group">
                <label htmlFor="languageId">Idioma</label>
                <select className="form-select"
                    id="languageId" name="languageId" value={elemento.languageId}
                    onChange={handleChange} >
                    {idiomas.map(item => <option key={item.id} value={item.id}>{item.name}</option>)}
                </select>
                <ValidationMessage msg={msgErr.languageId} />
            </div>
            <div className="form-group">
                <label htmlFor="rating">Clasificación</label>
                <select className="form-select"
                    id="rating" name="rating" value={elemento.rating}
                    onChange={handleChange} >
                    {clasificaciones.map(item => <option key={item.key} value={item.key}>{item.value}</option>)}
                </select>
                <ValidationMessage msg={msgErr.rating} />
            </div>
            <div className="form-group">
                <label htmlFor="rentalDuration">Duración alquiler</label>
                <div className="input-group">
                    <input type="number" className="form-control"
                        id="rentalDuration" name="rentalDuration" value={elemento.rentalDuration}
                        onChange={handleChange} min={1} />
                    <span className="input-group-text">días</span>
                </div>
                <ValidationMessage msg={msgErr.rentalDuration} />
            </div>
            <div className="form-group">
                <label htmlFor="rentalRate">Coste alquiler</label>
                <div className="input-group">
                    <input type="number" step={.01} className="form-control"
                        id="rentalRate" name="rentalRate" value={elemento.rentalRate}
                        onChange={handleChange} min={0.01} />
                    <span className="input-group-text">€</span>
                </div>
                <ValidationMessage msg={msgErr.rentalRate} />
            </div>
            <div className="form-group">
                <label htmlFor="replacementCost">Coste reposición</label>
                <div className="input-group">
                    <input type="number" step={.01} className="form-control"
                        id="replacementCost" name="replacementCost" value={elemento.replacementCost}
                        onChange={handleChange} min={0.01} />
                    <span className="input-group-text">€</span>
                </div>
                <ValidationMessage msg={msgErr.replacementCost} />
            </div>
            <div className="form-group">
                <label htmlFor="categories">Categorías</label>
                <div className="input-group">
                    <select className="form-select" id="categories" name="categories" onChange={ev => setIdCategoria(ev.target.value)}>
                        {categories.map(item => <option key={item.id} value={item.id}>{item.name}</option>)}
                    </select>
                    <button className="btn btn-outline-secondary" type="button" onClick={() => {
                        if (elemento.categories.includes(idCategoria)) return
                        elemento.categories.push(idCategoria)
                        setElemento({ ...elemento })
                    }} ><i className="far fa-plus"></i></button>
                </div>
                <ValidationMessage msg={msgErr.categories} />
                <ul>
                    {elemento.categories.map((item, index) =>
                        <li key={index}>{categories.find(ele => ele.id == item)?.categoria} <button className="btn btn-link" type="button"
                            title='Quita categoría' onClick={() => {
                                elemento.categories.splice(index, 1)
                                setElemento({ ...elemento })
                            }}><i className="far fa-trash-alt"></i></button></li>
                    )}
                </ul>
            </div>
            <div className="form-group">
                <label htmlFor="actors">Actores y Actrices</label>
                <div className="input-group">
                    <select className="form-select" id="actors" name="actors" onChange={ev => setIdActor(ev.target.value)}>
                        {actores.map(item => <option key={item.actorId} value={item.actorId}>{item.nombre}</option>)}
                    </select>
                    <button className="btn btn-outline-secondary" type="button" onClick={() => {
                        if (elemento.actors.includes(idActor)) return
                        elemento.actors.push(idActor)
                        setElemento({ ...elemento })
                    }} ><i className="far fa-plus"></i></button>
                </div>
                <ValidationMessage msg={msgErr.actors} />
                <ul>
                    {elemento.actors.map((item, index) =>
                        <li key={index}>{titleCase(actores.find(ele => ele.actorId == item)?.nombre)} <button className="btn btn-link" type="button"
                            title='Quita actor' onClick={() => {
                                elemento.actors.splice(index, 1)
                                setElemento({ ...elemento })
                            }}><i className="far fa-trash-alt"></i></button></li>
                    )}
                </ul>
            </div>
            <div className="btn-group">
                <button className="btn btn-success" type="button" disabled={invalid} onClick={onSend} >Enviar</button>
                {!props.isAdd && <button type="button" className="btn btn-danger" title="Borrar" onClick={e => props.onDelete(elemento.filmId)}>Borrar</button>}
                <button className="btn btn-primary" type="button" onClick={onCancel} >Volver</button>
            </div>
        </form>
    );
}