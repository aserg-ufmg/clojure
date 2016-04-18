/**
 *   Copyright (c) Rich Hickey. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 * 	 the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

/* chouser Jun 23, 2010 */

package clojure.lang;

import java.math.BigInteger;
import java.math.BigDecimal;

public final class ClojureBigInteger extends Number implements IHashEq{

final public long lpart;
final public BigInteger bipart;

final public static ClojureBigInteger ZERO = new ClojureBigInteger(0,null);
final public static ClojureBigInteger ONE = new ClojureBigInteger(1,null);


//must follow Long
public int hashCode(){
	if(bipart == null)
		return (int) (this.lpart ^ (this.lpart >>> 32));
	return bipart.hashCode();
}

public int hasheq(){
	if(bipart == null)
		return Murmur3.hashLong(lpart);
	return bipart.hashCode();

}

public boolean equals(Object obj){
	if(this == obj)
		return true;
	if(obj instanceof ClojureBigInteger)
		{
		ClojureBigInteger o = (ClojureBigInteger) obj;
		if(bipart == null)
			return o.bipart == null && this.lpart == o.lpart;
		return o.bipart != null && this.bipart.equals(o.bipart);
		}
	return false;
}

private ClojureBigInteger(long lpart, BigInteger bipart){
	this.lpart = lpart;
	this.bipart = bipart;
}

public static ClojureBigInteger fromBigInteger(BigInteger val){
	if(val.bitLength() < 64)
		return new ClojureBigInteger(val.longValue(), null);
	else
		return new ClojureBigInteger(0, val);
}

public static ClojureBigInteger fromLong(long val){
	return new ClojureBigInteger(val, null);
}

public BigInteger toBigInteger(){
	if(bipart == null)
		return BigInteger.valueOf(lpart);
	else
		return bipart;
}

public BigDecimal toBigDecimal(){
	if(bipart == null)
		return BigDecimal.valueOf(lpart);
	else
		return new BigDecimal(bipart);
}

///// java.lang.Number:

public int intValue(){
	if(bipart == null)
		return (int) lpart;
	else
		return bipart.intValue();
}

public long longValue(){
	if(bipart == null)
		return lpart;
	else
		return bipart.longValue();
}

public float floatValue(){
	if(bipart == null)
			return lpart;
	else
		return bipart.floatValue();
}

public double doubleValue(){
	if(bipart == null)
		return lpart;
	else
		return bipart.doubleValue();
}

public byte byteValue(){
	if(bipart == null)
		return (byte) lpart;
	else
		return bipart.byteValue();
}

public short shortValue(){
	if(bipart == null)
		return (short) lpart;
	else
		return bipart.shortValue();
}

public static ClojureBigInteger valueOf(long val){
	return new ClojureBigInteger(val, null);
}

public String toString(){
	if(bipart == null)
		return String.valueOf(lpart);
	return bipart.toString();
}

public int bitLength(){
	return toBigInteger().bitLength();
}

public ClojureBigInteger add(ClojureBigInteger y) {
    if ((bipart == null) && (y.bipart == null)) {
        long ret = lpart + y.lpart;
        if ((ret ^ lpart) >= 0 || (ret ^ y.lpart) >= 0)
            return ClojureBigInteger.valueOf(ret);
    }
    return ClojureBigInteger.fromBigInteger(this.toBigInteger().add(y.toBigInteger()));
}

public ClojureBigInteger multiply(ClojureBigInteger y) {
    if ((bipart == null) && (y.bipart == null)) {
        long ret = lpart * y.lpart;
            if (y.lpart == 0 ||
                (ret / y.lpart == lpart && lpart != Long.MIN_VALUE))
                return ClojureBigInteger.valueOf(ret);
        }
    return ClojureBigInteger.fromBigInteger(this.toBigInteger().multiply(y.toBigInteger()));
}

public ClojureBigInteger quotient(ClojureBigInteger y) {
    if ((bipart == null) && (y.bipart == null)) {
        if (lpart == Long.MIN_VALUE && y.lpart == -1)
            return ClojureBigInteger.fromBigInteger(this.toBigInteger().negate());
        return ClojureBigInteger.valueOf(lpart / y.lpart);
    }
    return ClojureBigInteger.fromBigInteger(this.toBigInteger().divide(y.toBigInteger()));
}

public ClojureBigInteger remainder(ClojureBigInteger y) {
    if ((bipart == null) && (y.bipart == null)) {
        return ClojureBigInteger.valueOf(lpart % y.lpart);
    }
    return ClojureBigInteger.fromBigInteger(this.toBigInteger().remainder(y.toBigInteger()));
}

public boolean lt(ClojureBigInteger y) {
    if ((bipart == null) && (y.bipart == null)) {
        return lpart < y.lpart;
    }
    return this.toBigInteger().compareTo(y.toBigInteger()) < 0;
}

@WarnBoxedMath(false)
static public Number reduceBigInt(ClojureBigInteger val){
	if(val.bipart == null)
		return Numbers.num(val.lpart);
	else
		return val.bipart;
}

}
