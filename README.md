# 66Taxis
------------------------------------------------

Projeto para simular uma cidade com táxis e passageiros. 
Os táxis ficam circulando aleatoriamente pelas ruas da cidade esperando chamadas de passageiros. 
Quando recebe uma chamada, o taxista deve ir até o local do passageiro e levá-lo para seu destino.

### Principios assumidos
  - Taxis se movem uma posição no grid por tempo
  - Movimento na diagonal não é permitido
  - Ao adicionar um passageiro o taxi mais próximo é avisado.
  - Quando o taxi chega até o passageiro, o mesmo o leva até o seu destino.
  - O taxi deve percorrer o menor caminho até o destino do passageiro
  - O mesmo local (posição), pode conter vários passageiros e taxis.
  - O mapa da cidade é provido pelo formato do seguinte arquivo <adicionar arquivo>
  
### API
**POST /taxi**

Exemplo de request:
```json
{
  "taxi": {
    "x": 4,
    "y": 5
  }
}
```

Exemplo de response:
```json
```

**POST /passenger**
```json
{
  "passenger": {
    "location": {
      "x": 7,
      "y": 7
    },
    "destination": {
      "x": 5,
      "y": 12
    }
  }
}
```

**POST /time**
```json
```

**POST /restart**
```json
```

**GET /state**
```json
```

##### Legenda:
- F = Taxi Livre
- O = Taxi Ocupado
- E = Taxi em rota
- P = Passageiro
- X = Bloqueio
- _ = Caminho Livre