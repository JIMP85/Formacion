import './App.css';
import './biblioteca/comunes.css'

import React, { Component } from 'react'
import { ErrorBoundary } from './biblioteca/comunes';
import { ActoresMnt } from './componentes/actores';
import IdiomasMnt from './componentes/idiomas';

export default class App extends Component {
  constructor(props) {
    super(props)
    this.state = {
      cont: 0,
      main: 0
    }

    this.menu = [
      { texto: 'idiomas', url: '/idiomas', componente: <IdiomasMnt /> },
      { texto: 'actores', url: '/actores', componente: <ActoresMnt /> },
      
      
    ]
  }

  render() {
    return (
      <>
        <Cabecera menu={this.menu} actual={this.state.main} onSelectMenu={indice => this.setState({ main: indice })} />
        <main className='container-fluid'>
          <ErrorBoundary>
            {this.menu[this.state.main].componente}
          </ErrorBoundary>
        </main>
        <Pie />
      </>
    )
  }
}



function Cabecera(props) {
  return (
    <header>
      <nav className="navbar navbar-expand-lg bg-body-tertiary">
        <div className="container-fluid">
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon" />
          </button>
          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <Menu {...props} />
          </div>
        </div>
      </nav>

    </header>
  );
}

function Menu({ menu, actual, onSelectMenu }) {
  return (
    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
      {menu.map((item, index) =>
        <li key={index} className="nav-item">
          <a className={'nav-link' + (actual === index ? ' active' : '')} aria-current="page" href="."
            onClick={ev => { 
              ev.preventDefault()
              onSelectMenu && onSelectMenu(index) 
            }}>{item.texto}</a>
        </li>
      )
      }
    </ul>
  );
}

function Pie() {
  return null;
}


