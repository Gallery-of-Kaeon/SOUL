var one = require("./ONE.js");
var onePlus = require("./ONEPlus.js");
var philosophersStone = require("./PhilosophersStone.js");

class SOULCoreModel {

	train(input, output, correlation) {
		
	}

	generate(input, correlation) {
		
	}

	correlate(input, output) {
		
	}

	load(corpus) {
		
	}

	write() {
		
	}
}

class SOULCoreNeuralNetwork {
	
	constructor() {
		this.weights = []; // [[weight, ... etc.], ... etc.]
	}
	
	getGradient(input) {
		
		let gradient = [];
		
		for(let i = 0; i < this.weights.length; i++) {
			
			let sum = 0;
			
			for(let j = 0; j < input.length; j++)
				sum += 1 - Math.abs((j / input.length) - (i / this.weights.length));
			
			gradient.push(sigmoid(sum));
		}
		
		return gradient;
	}
	
	train(input, output, correlation) {
			
		while(this.weights.length < output.length)
			this.weights.push([]);
		
		for(let i = 0; i < this.weights.length; i++) {
			
			while(this.weights[i].length < output.length)
				this.weights[i].push(0);
		}
		
		let train = this.generate(input, 1);
		
		for(let i = 0; i < train.length; i++) {
		
			let error = output[i] - train[i];
			
			if(error == 0)
				continue;
			
			let weight = 0;
			
			for(let j = 0; j < this.weights.length; j++)
				weight += this.weights[j][i];
			
			for(let j = 0; j < this.weights.length; j++)
				this.weights[j][i] += (this.weights[j][i] / weight) * error * correlation;
		}
	}
	
	generate(input, correlation) {
		
		let gradient = this.getGradient(input);
		
		let output = [];
		
		for(let i = 0; i < gradient.length; i++) {
			
			let sum = 0;
			
			for(let j = 0; j < this.weights[i].length; j++)
				sum += gradient[i] * this.weights[i][j] * correlation;
			
			output.push(sigmoid(sum));
		}
		
		return output;
	}
	
	correlate(input, output) {
		
		let value = this.generate(input, 1);
		
		let correlation = 1;
		
		for(let i = 0; i < output.size; i++) {
			
			if(output[i] == 0 || value[i] == 0) {
			
				if(output[i] != 0 || value[i] != 0)
					continue;
				
				correlation *=
					output[i] != 0 ?
						1 - sigmoid(value[i]) :
						1 - sigmoid(output[i]);
			}
			
			correlation *=
				output[i] > value[i] ?
					value[i] / output[i] :
					output[i] / value[i];
		}
		
		return correlation;
	}
}

class DefaultSOULCoreModel extends SOULCoreModel {
	
	constructor() {
	
		super();
	
		this.size = new SOULCoreNeuralNetwork();
		this.data = new SOULCoreNeuralNetwork();
	}
	
	binarySizeToNumber(size) {
		
		let number = 0;
		
		for(let i = 0; i < size; i++)
			number += size[i] < .5 ? 0 : Math.pow(2, i);
		
		return number;
	}
	
	numberToBinarySize(number) {
		
		let binary = number.toString(2);
		
		let size = [];
		
		for(let i = 0; i < binary.length; i++) {
		
			if(binary.charAt(i) == '0')
				size.push(0);
			
			else
				size.push(1);
		}
				
		return size;
	}
	
	train(input, output, correlation) {
		this.size.train(input, this.numberToBinarySize(output.length), correlation);
		this.data.train(input, output, correlation);
	}
	
	generate(input, correlation) {
		
		let numbers = stringToNumbers(input);
		
		let size = this.binarySizeToNumber(this.size.generate(numbers, correlation));
		let data = this.data.generate(numbers, correlation);
		
		if(size < data.length)
			data = data.slice(0, size);
		
		// This may be omitted later.
		while(size > data.length)
			data.push(0);
		
		return numbersToString(data);
	}
	
	correlate(input, output) {
	
		return
			this.data.correlate(
				input,
				output) *
			this.size.correlate(
				input,
				numberToBinarySize(
					output.length));
	}

	load(corpus) {
		// STUB
	}

	write() {
		// STUB
	}
}

class SOULCore {

	constructor() {
		
		this.corpus = []; // [[input, output, correlation], ... etc.]
		
		this.models = [new DefaultSOULCoreModel()];
		this.model = this.models[0];
	}

	train(input, output, correlation) {
		
		this.corpus.push([input, output, correlation]);
		
		for(let i = 0; i < this.models.length; i++)
			this.models[i].train(input, output, correlation);
	}

	generate(input, correlation) {
		
		if(correlation == null)
			correlation = 1;
		
		this.model.generate(input, correlation);
	}

	correlate(input, output) {
		this.model.correlate(input, output);
	}

	load(corpus) {
		// STUB
	}

	write() {
		// STUB
	}
	
	addModel(model) {
		this.models.push(model);
	}
	
	setModel(model) {
		// STUB
	}
}

class SOUL extends philosophersStone.PhilosophersStone {
	
	constructor() {
		this.cores = []; // [[gate, transform], ... etc.]
	}
	
	onCall(packet) {
		// STUB
	}
	
	onOperation(packet) {
		
	}
}

function connectSOUL(soulStone, stone, cores, transform) {
	// STUB
}

function disconnectSOUL(soulStone, stone) {
	// STUB
}

function setGatewayCore(soulStone, stone, core) {
	// STUB
}

function setTransformationCore(soulStone, stone, core) {
	// STUB
}

function getGatewayCore(soulStone, stone) {
	// STUB
}

function getTransformationCore(soulStone, stone) {
	// STUB
}
	
function stringToNumbers(string) {
	
	let numbers = [];
	
	for(let i = 0; i < string.length; i++)
		numbers.push(string.charCodeAt(i));
	
	return numbers;
}
	
function numbersToString(numbers) {

	let string = "";
	
	for(let i = 0; i < numbers.length; i++)
		string += String.fromCharCode(numbers[i]);
	
	return string;
}

function sigmoid(number) {
	return 1 / (1 + Math.pow(Math.E, -number));
}

function inverseSigmoid(number) {
	return Math.log(number / (1 - number));
}

module.exports = {

	SOULCoreModel,
	SOULCoreNeuralNetwork,
	DefaultSOULCoreModel,
	SOULCore,
	SOUL,
	connectSOUL,
	disconnectSOUL,
	setGatewayCore,
	setTransformationCore,
	getGatewayCore,
	getTransformationCore,
	stringToNumbers,
	numbersToString,
	sigmoid,
	inverseSigmoid
};