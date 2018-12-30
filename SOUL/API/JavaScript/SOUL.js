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

class SoulCoreNeuralNetwork {
	
	constructor(size) {
		this.weights = []; // [[weight, ... etc.], ... etc.]
	}
	
	function getGradient(input) {
		
		gradient = [];
		
		for(let i = 0; i < weights.length; i++) {
			
			sum = 0;
			
			for(let j = 0; j < input.length; j++)
				sum += 1 - Math.abs((j / input.length) - (i / weights.length));
			
			gradient.push(sigmoid(sum));
		}
		
		return gradient;
	}
	
	function generate(input, correlation) {
		
		gradient = getGradient(input);
		
		// STUB
	}
	
	function train(input, output, correlation) {
		// STUB
	}
	
	function correlate(input, output) {
		// STUB
	}
}

class DefaultSOULCoreModel extends SOULCoreModel {
	
	constructor() {
		this.size = new SOULCoreNeuralNetwork();
		this.data = new SOULCoreNeuralNetwork();
	}
	
	function binarySizeToNumber(size) {
		
		let number = 0;
		
		for(let i = 0; i < size; i++)
			number += size[i] < .5 ? 0 : Math.pow(2, i);
		
		return number;
	}
	
	function numberToBinarySize(number) {
		
		let size = [];
		
		// STUB
		
		return size;
	}
	
	generate(input, correlation) {
		
		let numbers = stringToNumbers(input);
		
		let size = binarySizeToNumber(this.size.generate(numbers, correlation));
		let data = this.data.generate(numbers, correlation);
		
		if(size < data.length)
			data = data.slice(0, size);
		
		// This may be omitted later.
		while(size > data.length)
			data.push(0);
		
		return numbersToString(dataOutput);
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
		this.model = models[0];
	}

	train(input, output, correlation) {
		
		for(let i = 0; i < models.length; i++)
			models[i].train(input, output, correlation);
	}

	generate(input, correlation) {
		model.generate(input, correlation);
	}

	correlate(input, output) {
		model.correlate(input, output);
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
	
	constructor {
		this.cores = []; // [[gate, transform], ... etc.]
	}
	
	onCall(packet) {
		// STUB
	}
	
	onOperation(packet) {
		
	}
}

function connectSOUL(soulStone, stone) {
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
	numbersToString
};