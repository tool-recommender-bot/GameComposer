
var Hashtable=(function(UNDEFINED){var FUNCTION="function",STRING="string",UNDEF="undefined";if(typeof encodeURIComponent==UNDEF||Array.prototype.splice===UNDEFINED||Object.prototype.hasOwnProperty===UNDEFINED){return null;}
function toStr(obj){return(typeof obj==STRING)?obj:""+obj;}
function hashObject(obj){var hashCode;if(typeof obj==STRING){return obj;}else if(typeof obj.hashCode==FUNCTION){hashCode=obj.hashCode();return(typeof hashCode==STRING)?hashCode:hashObject(hashCode);}else{return toStr(obj);}}
function merge(o1,o2){for(var i in o2){if(o2.hasOwnProperty(i)){o1[i]=o2[i];}}}
function equals_fixedValueHasEquals(fixedValue,variableValue){return fixedValue.equals(variableValue);}
function equals_fixedValueNoEquals(fixedValue,variableValue){return(typeof variableValue.equals==FUNCTION)?variableValue.equals(fixedValue):(fixedValue===variableValue);}
function createKeyValCheck(kvStr){return function(kv){if(kv===null){throw new Error("null is not a valid "+kvStr);}else if(kv===UNDEFINED){throw new Error(kvStr+" must not be undefined");}};}
var checkKey=createKeyValCheck("key"),checkValue=createKeyValCheck("value");function Bucket(hash,firstKey,firstValue,equalityFunction){this[0]=hash;this.entries=[];this.addEntry(firstKey,firstValue);if(equalityFunction!==null){this.getEqualityFunction=function(){return equalityFunction;};}}
var EXISTENCE=0,ENTRY=1,ENTRY_INDEX_AND_VALUE=2;function createBucketSearcher(mode){return function(key){var i=this.entries.length,entry,equals=this.getEqualityFunction(key);while(i--){entry=this.entries[i];if(equals(key,entry[0])){switch(mode){case EXISTENCE:return true;case ENTRY:return entry;case ENTRY_INDEX_AND_VALUE:return[i,entry[1]];}}}
return false;};}
function createBucketLister(entryProperty){return function(aggregatedArr){var startIndex=aggregatedArr.length;for(var i=0,entries=this.entries,len=entries.length;i<len;++i){aggregatedArr[startIndex+i]=entries[i][entryProperty];}};}
Bucket.prototype={getEqualityFunction:function(searchValue){return(typeof searchValue.equals==FUNCTION)?equals_fixedValueHasEquals:equals_fixedValueNoEquals;},getEntryForKey:createBucketSearcher(ENTRY),getEntryAndIndexForKey:createBucketSearcher(ENTRY_INDEX_AND_VALUE),removeEntryForKey:function(key){var result=this.getEntryAndIndexForKey(key);if(result){this.entries.splice(result[0],1);return result[1];}
return null;},addEntry:function(key,value){this.entries.push([key,value]);},keys:createBucketLister(0),values:createBucketLister(1),getEntries:function(destEntries){var startIndex=destEntries.length;for(var i=0,entries=this.entries,len=entries.length;i<len;++i){destEntries[startIndex+i]=entries[i].slice(0);}},containsKey:createBucketSearcher(EXISTENCE),containsValue:function(value){var entries=this.entries,i=entries.length;while(i--){if(value===entries[i][1]){return true;}}
return false;}};function searchBuckets(buckets,hash){var i=buckets.length,bucket;while(i--){bucket=buckets[i];if(hash===bucket[0]){return i;}}
return null;}
function getBucketForHash(bucketsByHash,hash){var bucket=bucketsByHash[hash];return(bucket&&(bucket instanceof Bucket))?bucket:null;}
function Hashtable(){var buckets=[];var bucketsByHash={};var properties={replaceDuplicateKey:true,hashCode:hashObject,equals:null};var arg0=arguments[0],arg1=arguments[1];if(arg1!==UNDEFINED){properties.hashCode=arg0;properties.equals=arg1;}else if(arg0!==UNDEFINED){merge(properties,arg0);}
var hashCode=properties.hashCode,equals=properties.equals;this.properties=properties;this.put=function(key,value){checkKey(key);var hash=hashCode(key),bucket,bucketEntry,oldValue=null;bucket=getBucketForHash(bucketsByHash,hash);if(bucket){bucketEntry=bucket.getEntryForKey(key);if(bucketEntry){if(properties.replaceDuplicateKey){bucketEntry[0]=key;}
oldValue=bucketEntry[1];bucketEntry[1]=value;}else{bucket.addEntry(key,value);}}else{bucket=new Bucket(hash,key,value,equals);buckets.push(bucket);bucketsByHash[hash]=bucket;}
return oldValue;};this.get=function(key){checkKey(key);var hash=hashCode(key);var bucket=getBucketForHash(bucketsByHash,hash);if(bucket){var bucketEntry=bucket.getEntryForKey(key);if(bucketEntry){return bucketEntry[1];}}
return null;};this.containsKey=function(key){checkKey(key);var bucketKey=hashCode(key);var bucket=getBucketForHash(bucketsByHash,bucketKey);return bucket?bucket.containsKey(key):false;};this.containsValue=function(value){checkValue(value);var i=buckets.length;while(i--){if(buckets[i].containsValue(value)){return true;}}
return false;};this.clear=function(){buckets.length=0;bucketsByHash={};};this.isEmpty=function(){return!buckets.length;};var createBucketAggregator=function(bucketFuncName){return function(){var aggregated=[],i=buckets.length;while(i--){buckets[i][bucketFuncName](aggregated);}
return aggregated;};};this.keys=createBucketAggregator("keys");this.values=createBucketAggregator("values");this.entries=createBucketAggregator("getEntries");this.remove=function(key){checkKey(key);var hash=hashCode(key),bucketIndex,oldValue=null;var bucket=getBucketForHash(bucketsByHash,hash);if(bucket){oldValue=bucket.removeEntryForKey(key);if(oldValue!==null){if(bucket.entries.length==0){bucketIndex=searchBuckets(buckets,hash);buckets.splice(bucketIndex,1);delete bucketsByHash[hash];}}}
return oldValue;};this.size=function(){var total=0,i=buckets.length;while(i--){total+=buckets[i].entries.length;}
return total;};}
Hashtable.prototype={each:function(callback){var entries=this.entries(),i=entries.length,entry;while(i--){entry=entries[i];callback(entry[0],entry[1]);}},equals:function(hashtable){var keys,key,val,count=this.size();if(count==hashtable.size()){keys=this.keys();while(count--){key=keys[count];val=hashtable.get(key);if(val===null||val!==this.get(key)){return false;}}
return true;}
return false;},putAll:function(hashtable,conflictCallback){var entries=hashtable.entries();var entry,key,value,thisValue,i=entries.length;var hasConflictCallback=(typeof conflictCallback==FUNCTION);while(i--){entry=entries[i];key=entry[0];value=entry[1];if(hasConflictCallback&&(thisValue=this.get(key))){value=conflictCallback(key,thisValue,value);}
this.put(key,value);}},clone:function(){var clone=new Hashtable(this.properties);clone.putAll(this);return clone;}};Hashtable.prototype.toQueryString=function(){var entries=this.entries(),i=entries.length,entry;var parts=[];while(i--){entry=entries[i];parts[i]=encodeURIComponent(toStr(entry[0]))+"="+encodeURIComponent(toStr(entry[1]));}
return parts.join("&");};return Hashtable;})();