var user1 = {
    name: 'Rashmi',
    age: 30,
    getYearsLeftForRetirement: function(retirementAge) {
        return retirementAge - this.age;
    }
};

var yearsLeft = user1.getYearsLeftForRetirement();


console.log(user1.getYearsLeftForRetirement(60)); //  ?
console.log(yearsLeft(60)); // ?
