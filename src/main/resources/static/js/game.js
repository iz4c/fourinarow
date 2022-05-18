const game = document.getElementById("game");
const WIDTH = 7;
const HEIGHT = 6;
const PLAYER_ONE = {
    tile: 1,
    colour: 'red'
};
const PLAYER_TWO = {
    tile: -1,
    colour: 'blue'
};

let INPUT_LOCKED = false;

const boardWrapper = document.createElement("div");
boardWrapper.setAttribute("style", "display:inline");
const board = document.createElement("table");
const tiles = [];
const state = new Array(WIDTH).fill(0).map(() => new Array(HEIGHT).fill(0));

for (let row = 0; row < HEIGHT; row++) {
    const rowEl = document.createElement("tr");
    for (let col = 0; col < WIDTH; col++) {
        const cell = document.createElement("td");
        const tile = document.createElement("div");
        const tile_id = "tile_" + col + "_" + row;
        tile.id = tile_id;
        const canvas = document.createElement("canvas");
        canvas.id = tile_id + "_canv";
        cell.setAttribute("onclick", "tileClick(" + col + ", " + row + ")");
        canvas.width = 80;
        canvas.height = 80;

        tiles[col * HEIGHT + row] = tile_id;
        tile.appendChild(canvas);
        cell.appendChild(tile);
        rowEl.appendChild(cell);
    }
    board.appendChild(rowEl);
}

boardWrapper.appendChild(board);
game.appendChild(boardWrapper);

function fillCircle(canvas, x, y, radius, color, hollow=false) {
    let context = canvas.getContext("2d");
    context.fillStyle = color;

    context.beginPath();
    context.arc(x, y, radius, 0, 2 * Math.PI);

    hollow ? context.stroke() : context.fill();
}

function tileClick(col, row) {
    if (INPUT_LOCKED || isColumnFull(col)) return;

    for (let rowIdx = 0; rowIdx < HEIGHT; rowIdx++) {
        // check if the row is the bottom row, or if the row below already has a tile
        if (rowIdx + 1 >= HEIGHT || state[col][rowIdx + 1] !== 0) {
            state[col][rowIdx] = PLAYER_ONE.tile;

            updateBoard();

            INPUT_LOCKED = true;  // lock input until computer has played
            const xmlHttp = new XMLHttpRequest();
            const url = '/move/' + WIDTH + "/" + HEIGHT + "/" + getBoardString(state);

            xmlHttp.open( "GET", url, true );
            xmlHttp.onload = function (e) {
                if (xmlHttp.status === 200) {
                    const response = JSON.parse(xmlHttp.responseText);
                    const result = response.result;
                    const column = parseInt(response.column);

                    if (column !== -1) {
                        const row = lowestFreeTile(column);
                        state[column][row] = PLAYER_TWO.tile;
                        updateBoard();
                    }

                    if (result === "CONTINUE") {
                        INPUT_LOCKED = false;  // unlock input for next move
                    } else {
                        const resultEl = document.createElement("h1");
                        let resultText;
                        switch (result) {
                            case "X_WIN":
                                resultText = "Red wins!";
                                break;
                            case "O_WIN":
                                resultText = "Blue wins!";
                                break;
                            case "DRAW":
                                resultText = "Draw!";
                                break;
                            default:
                                // this shouldn't happen
                                resultText = "Unknown result: " + result;
                                break;
                        }

                        resultEl.innerHTML = resultText + " <a href=''>(Click to play again)</a>";
                        boardWrapper.appendChild(resultEl);
                    }
                }
            }
            xmlHttp.send( null );
            break;
        }
    }
}

function isColumnFull(columnIdx) {
    for (let row = 0; row < HEIGHT; row++) {
        if (state[columnIdx][row] === 0) return false;
    }

    return true;
}

function lowestFreeTile(columnIdx) {
    for (let row = HEIGHT - 1; row >= 0; row--) {
        if (state[columnIdx][row] === 0) return row;
    }

    return -1;
}

function getBoardString(board_data) {
    let out = "";
    for (let col = 0; col < WIDTH; col++) {
        for (let row = 0; row < HEIGHT; row++) {
            switch (board_data[col][row]) {
                case PLAYER_ONE.tile:
                    out += "X";
                    break;
                case PLAYER_TWO.tile:
                    out += "O";
                    break;
                case 0:
                    out += "-";
                    break;
                default:
                    out += "?";
                    break;
            }
        }
    }

    return out;
}

function updateBoard() {
    for (let col = 0; col < WIDTH; col++) {
        for (let row = 0; row < HEIGHT; row++) {
            let canvas = document.getElementById("tile_" + col + "_" + row + "_canv");
            let context = canvas.getContext("2d");
            context.clearRect(0, 0, canvas.width, canvas.height);

            switch (state[col][row]) {
                case PLAYER_ONE.tile:
                    fillCircle(canvas, 40, 40, 40, PLAYER_ONE.colour);
                    break;
                case PLAYER_TWO.tile:
                    fillCircle(canvas, 40, 40, 40, PLAYER_TWO.colour);
                    break;
                default:
                    fillCircle(canvas, 40, 40, 40, 'black', true);
            }
        }
    }
}

updateBoard();