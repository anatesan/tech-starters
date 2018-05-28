// Write a function to find the sum of 2 numbers and which can be invoked as below:
// sum(1)(2);
var aggr = 0;

var sum = function(value) {
  aggr = aggr + value;
  console.log('In accum: ' + aggr);
  return sum;
}

// sum(1);
// sum(2);
sum(1)(2);
sum(1)(2)(3)(4);
