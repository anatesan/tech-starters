// 3) Write a function to return the vowels in a string such that it can be invoked as below:
// "ABC".getVowels();  //[A]
// "hello".getVowels();  //[e,o]

String.prototype.getVowels = function() {
  var str = this;
  var vowels = ['a', 'e', 'i', 'o', 'u'];
  var foundVowels = [];
  for (var i = 0, len = str.length; i < len; i++) {
    if (vowels.indexOf(str[i].toLowerCase()) !== -1) {
      foundVowels.push(str[i]);
    }
  }
  return foundVowels;
}

console.log(typeof String.prototype.getVowels);
var results = "ABC".getVowels();
console.log(results);
console.log("Hello There".getVowels());
