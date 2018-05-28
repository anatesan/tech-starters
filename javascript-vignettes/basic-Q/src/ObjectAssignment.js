var num = 10,
  name = "Walmart",
  obj1 = {
    value: "first value"
  },
  obj2 = {
    value: "second value"
  },
  obj3 = obj2;

function change(num, name, obj1, obj2) {
  num = num * 10;
  name = "Walmart labs";
  obj1 = obj2;
  obj2.value = "new value";
}

change(num, name, obj1, obj2);

console.log(num);  //100
console.log(name); //Walmart labs
console.log(obj1.value); // new value
console.log(obj2.value); // new value
console.log(obj3.value); // second value
