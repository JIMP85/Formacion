import React, { Component } from 'react'
import { ErrorMessage, Esperando, PaginacionCmd as Paginacion } from '../comunes'

class Ficha extends Component {
    render() {
        return (
            <div className="card" style={{ width: "14rem" }}>
                {this.props.visible && <img src={this.props.download_url} className="card-img-top" alt={`Foto ${this.props.id} de ${this.props.author}`} />}
                <div className="card-body">
                    <h5 className="card-title">{this.props.author}</h5>
                    {!this.props.visible && <div className="card-text">
                        <p>Dimensión: {this.props.width}x{this.props.height}</p>
                        <input type="button" value="Ver foto" className="btn btn-primary" onClick={() => this.props.onVer && this.props.onVer(this.props.id)} />
                    </div>}
                </div>
            </div>
        )
    }
}
export default class Muro extends Component {
    constructor(props) {
        super(props);
        this.state = {
            listado: [],
            errorMsg: null,
            loading: true
        };
        this.page = 0;
        this.totalRecords = 1000;
        this.rows = 35;
        this.paginas = Math.ceil(1000 / this.rows);
        this.first = 0;
    }

    setError(msg) {
        this.setState({ error: msg, loading: false })
    }

    load(pagina = 0) {
        this.setState({ loading: true, errorMsg: null })
        fetch(`https://picsum.photos/v2/list?page=${pagina + 1}&limit=${this.rows}`)
            .then(
                resp => {
                    if (resp.ok) {
                        resp.json().then(
                            data => {
                                this.page = pagina;
                                this.first = pagina ? (pagina * this.rows) : 0
                                this.setState({ listado: data.map(item => ({ ...item, visible: false })), loading: false })
                            },
                            err => this.setError(`ERROR (respuesta): ${err.status}: ${err.statusText}`)
                        )
                    } else {
                        this.setError(`ERROR (servidor): ${resp.status}: ${resp.statusText}`)
                    }
                },
                err => {
                    this.setError(`ERROR (petición): ${err.status}: ${err.statusText}`)
                }
            )
    }
    mostrar(indice) {
        this.state.listado[indice].visible = true;
        this.setState({ listado: [...this.state.listado] })
    }
    render() {
        if (this.state.loading)
            return <Esperando />
        return (
            <div>
                {this.state.error && <ErrorMessage msg={this.state.error} />}
                <Paginacion actual={this.page} total={this.paginas} onChange={num => this.load(num)} />
                <main className='container-fluid'>
                    <div className='row'>
                        {this.state.listado.map((item, index) => <Ficha key={item.id} {...item} onVer={() => this.mostrar(index)} />)}
                    </div>
                </main>
            </div>
        )
    }

    componentDidMount() {
        this.load();
    }
}


