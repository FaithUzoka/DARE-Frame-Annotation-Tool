// eslint-disable-next-line no-unused-vars
import React, {Component} from 'react';
import axios from 'axios';
import './style.css';
import Select from 'react-select'

export default class VideoComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            optionList: [],
            value: "",
            value2: "",
            value3: "",
            frameSlide: [],
            videoObject: [],
            correctedVideoObject: [],
            dummyCount: 0,
            count: 0,
            editingId: 0,
            frameNumber: 0,
            objectDetails: "",
            objectChoices: [
                {value: 1, label: "person", value2: "Person"},
                {value: 3, label: "car", value2: "Car"},
                {value: 6, label: "bus", value2: "Bus"},
                {value: 8, label: "truck", value2: "Truck"}
            ]
        };
        this.imgRef = React.createRef();
        this.canvasRef = React.createRef();
        this.drawList = this.drawList.bind(this)
        this.handleCanvas = this.handleCanvas.bind(this);
        this.handleNextFrame = this.handleNextFrame.bind(this);
        this.handlePreviousFrame = this.handlePreviousFrame.bind(this);
        this.handleDisplayResult = this.handleDisplayResult.bind(this);
        this.handleTypeText = this.handleTypeText.bind(this);
        this.componentDidMount = this.componentDidMount.bind(this);
        this.handleChangeApplication = this.handleChangeApplication.bind(this);
        this.handleMouseOver = this.handleMouseOver.bind(this);
        this.handleTextId = this.handleTextId.bind(this);
        this.initializeCoordinates = this.initializeCoordinates.bind(this);
    }


    componentDidMount() {
        axios.get("leaseFileAPI/v1/videoObject").then(res => {
            let frames = res.data;
            this.setState({frameSlide: []});
            this.setState({frameSlide: frames});
            console.log(this.state.frameSlide);
            axios.get("resultAPI/v1/videoObject").then(r => {
                let dummyData = r.data
                this.setState({videoObject: dummyData})
                console.log(this.state.videoObject)
                this.drawList(this.state.videoObject)
            })

            axios.get("correctedDisplayAPI/v1/videoObject").then(resp => {
                let dummyData = resp.data
                this.setState({correctedVideoObject: dummyData})
                console.log(this.state.correctedVideoObject)
            })
        }).catch(error => {
            console.log(error)
        })
    }




    async handleCorrectedResult() {
        let changedResponse =
            await
                axios.get("correctedDisplayAPI/v1/videoObject")
        console.log(changedResponse.data)
        this.setState({correctedVideoObject: changedResponse.data})
        this.drawList(this.state.correctedVideoObject)
    }

    async handleDisplayResult() {
       let response =
            await
                axios.get("resultAPI/v1/videoObject")
        this.setState({videoObject: []})
        this.setState({videoObject: response.data})
        console.log(this.state.videoObject)
        this.drawList(this.state.videoObject)
    }

    drawList(objList){
        const canvas = this.canvasRef.current
        const ctx = canvas.getContext('2d')
        console.log(this.canvasRef.current.style)
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        console.log(canvas.width);
        console.log(canvas.height);
        for(let i = 0; i < objList[this.state.count].length; i++){
            for(let j = 0; j < objList[this.state.count][j].coordinates.length; j++){
                let h = (objList[this.state.count][i].coordinates[3] - objList[this.state.count][i].coordinates[1])*1.2792963;
                let w = (objList[this.state.count][i].coordinates[2] - objList[this.state.count][i].coordinates[0]);
                let x = objList[this.state.count][i].coordinates[1]*1.2792963;
                let y = objList[this.state.count][i].coordinates[0];
                ctx.beginPath();
                ctx.rect(x,y,h,w);
                ctx.strokeStyle = "red";
                ctx.stroke();
                ctx.fillStyle = "red";
                ctx.fillRect(x,(y-7),h,7);
                ctx.fillStyle = "white"
                ctx.font = "7 pt sans-serif"
                ctx.fillText(objList[this.state.count][i].objectType + " " + objList[this.state.count][i].objectId.toString(), x, y)
                console.log(x);
                console.log(y);
                console.log(w);
                console.log(h);
            }
        }

    }

    handleChangeApplication() {
        let data = {
            "objectType": (this.state.optionList.label),
            "objectId": (this.state.editingId),
            "objectTypeCode": parseInt(this.state.optionList.value),
            "frameNumber": (this.state.frameNumber),
            "newObjectId": parseInt(this.state.value3)
        }
        axios.put("editResultAPI/v1/videoObject", data).then((data) => {
            console.log(data);
        }).catch((err) => {
            console.log(err);
        })
        console.log(this.state.optionList.value)
    }



    handleCanvas(event) {
        let xVar = event.clientX -100;
        let yVar = event.clientY -60;
        let objectId, objectType, objectTypeCode, coordinates, score;
        let dummyV, dummyV2, displayX, displayY, displayW, displayH;
        console.log(this.state.correctedVideoObject[this.state.count])
        for (let i = 0; i < this.state.correctedVideoObject[this.state.count].length; i++) {
            let displayCoordinates = this.initializeCoordinates(this.state.correctedVideoObject, i);
            displayX = displayCoordinates[0]
            displayY = displayCoordinates[1]
            displayW = displayCoordinates[2]
            displayH = displayCoordinates[3]
            console.log(displayX)
            console.log(displayY)
            console.log(displayW)
            console.log(displayH)
            if ((displayX <= xVar && xVar <= (displayX + displayW)) &&
                (displayY <= yVar && yVar <= (displayY + displayH))) {
                dummyV = this.state.correctedVideoObject[this.state.count][i].objectId
                dummyV2 = this.state.correctedVideoObject[this.state.count][i].frameNumber
                console.log(dummyV)
                console.log(this.state.correctedVideoObject[this.state.count][i])
                this.setState({editingId: dummyV}, () => {console.log(this.state.editingId);})
                this.setState({frameNumber: dummyV2}, () => {console.log(this.state.frameNumber);})
                objectId = dummyV.toString()
                objectType = this.state.correctedVideoObject[this.state.count][i].objectType.toString()
                objectTypeCode = this.state.correctedVideoObject[this.state.count][i].objectTypeCode.toString()
                coordinates = this.state.correctedVideoObject[this.state.count][i].coordinates.toString()
                score = this.state.correctedVideoObject[this.state.count][i].score.toString()
                let space = "      "
                this.setState({objectDetails: "Object Id: " +  objectId + space + "Object Type: " + objectType + space + "Object Type Code: " + objectTypeCode +
                        space + "Coordinates: " + "(" +coordinates + ")" + space + "Score: " + score})
                break
            }
            else{
                console.log("false")
            }

        }
            console.log(displayX)
            console.log(displayY)
            console.log(displayW)
            console.log(displayH)
            console.log(xVar)
            console.log(yVar)
        }

    handleMouseOver(event){
        let xVar = event.clientX-100;
        let yVar = event.clientY-60;
        let canvas = this.canvasRef.current;
        let cursorVal = false;
        const ctx = canvas.getContext('2d')
        let displayX, displayY, displayW, displayH;
        for (let i = 0; i < this.state.correctedVideoObject[this.state.count].length; i++) {
            let displayCoordinates = this.initializeCoordinates(this.state.correctedVideoObject, i);
            displayX = displayCoordinates[0]
            displayY = displayCoordinates[1]
            displayW = displayCoordinates[2]
            displayH = displayCoordinates[3]
            if((displayX <= xVar && xVar <= (displayX + displayW)) &&
                (displayY <= yVar && yVar <= (displayY + displayH))) {
                cursorVal = true
                break
            } else if ((xVar < displayX && (displayX + displayW) < xVar) &&
                (yVar < displayY && (displayY + displayH) < yVar)){
                cursorVal = false
            }
        }

        if(cursorVal === true){
            this.canvasRef.current.style.cursor = "pointer"
        } else{
            this.canvasRef.current.style.cursor = "default"
        }
    }

    handleNextFrame(){
        this.setState({count: this.state.count +1});
        console.log(this.state.count);
        this.drawList(this.state.videoObject);
    }

    handlePreviousFrame(){
        if( this.state.count> 0){
            this.setState({count: this.state.count -1})
        }
        console.log(this.state.count)
        this.drawList(this.state.correctedVideoObject)
    }

    handleTypeText(choiceValue){
        console.log(choiceValue)
        this.setState({optionList: choiceValue}, () => {console.log(this.state.optionList);})
    }

    handleTextId(event){
        this.setState({value3: event.target.value});
    }

    initializeCoordinates(objList, numb){
        let displayX, displayW, displayH, displayY;
        displayX = (objList[this.state.count][numb].coordinates[1] * 1.2792963)
        displayY = objList[this.state.count][numb].coordinates[0]
        displayW = ((objList[this.state.count][numb].coordinates[3] - this.state.correctedVideoObject[this.state.count][numb].coordinates[1]) * 1.4312963)
        displayH = (objList[this.state.count][numb].coordinates[2] - this.state.correctedVideoObject[this.state.count][numb].coordinates[0])
        return [displayX, displayY, displayW, displayH]
    }

    render() {
            const images = [this.state.frameSlide]
        for(let i = 1; i < this.state.frameSlide.length; i++){
            images[i] = {url: + " " + images[i]}
        }

        if (this.state.frameSlide.length === 0) {
            return (<div>loading ...</div>)
        } else {
            return (
                <React.Fragment>
                    <div className = "row">
                        <button
                            onClick={this.handleDisplayResult} className="btn btn-primary btn-sm m-3">Display Result
                        </button>
                        <Select
                            className= "list"
                            placeholder = "Select Object Type"
                            options = {this.state.objectChoices}
                            onChange = {this.handleTypeText}
                        />
                        <textarea
                            value={this.state.value3} onChange={this.handleTextId}
                            className="textStyle"
                            placeholder={"Set Id"}
                        />
                        <button
                            onClick={this.handleChangeApplication} className="btn btn-primary btn-sm m-3">Apply Change
                        </button>
                        <button
                            onClick={() => this.handleCorrectedResult()} className="btn btn-primary btn-sm m-3">Display Corrected
                            Result
                        </button>
                        <button
                            onClick={this.handlePreviousFrame} className="btn btn-primary btn-sm m-3">Prev Frame
                        </button>
                        <button
                            onClick={this.handleNextFrame} className="btn btn-primary btn-sm m-3">Next Frame
                        </button>
                    </div>
                    <div className = "outsideWrapper">
                        <div className = "insideWrapper">
                            <img className = "coveredImage" ref = {this.imgRef} src ={this.state.frameSlide[this.state.count]}/>
                            <canvas onClick={this.handleCanvas}
                                    onMouseMove={this.handleMouseOver}
                                    className = "coveringCanvas" ref = {this.canvasRef} width={1230} height={535}/>
                        </div>
                    </div>
                    <div className="form-group">
                        <label htmlFor="exampleDisabled" className="disabled">Object Info</label>
                        <input type="text" id="exampleDisabled" className="form-control" value = {this.state.objectDetails} disabled />
                    </div>


                </React.Fragment>
            );
        }
    }
}
