package de.rrze.jpwgen;

import java.io.Serializable;

import de.rrze.jpwgen.flags.impl.AtLeast2DigitsFlag;
import de.rrze.jpwgen.flags.impl.AtLeast2SymbolsFlag;
import de.rrze.jpwgen.flags.impl.ConsecCapitalsFlag;
import de.rrze.jpwgen.flags.impl.ConsecDigitsFlag;
import de.rrze.jpwgen.flags.impl.ConsecSymbolsFlag;
import de.rrze.jpwgen.flags.impl.EndsNoDigitFlag;
import de.rrze.jpwgen.flags.impl.EndsNoLowerCaseFlag;
import de.rrze.jpwgen.flags.impl.EndsNoSymbolFlag;
import de.rrze.jpwgen.flags.impl.EndsNoUpperCaseFlag;
import de.rrze.jpwgen.flags.impl.PwAmbiguousFlag;
import de.rrze.jpwgen.flags.impl.PwCapitalizeFlag;
import de.rrze.jpwgen.flags.impl.PwDigitsFlag;
import de.rrze.jpwgen.flags.impl.PwReducedSymbolsFlag;
import de.rrze.jpwgen.flags.impl.PwSymbolsFlag;
import de.rrze.jpwgen.flags.impl.SingleCapitalFlag;
import de.rrze.jpwgen.flags.impl.SingleDigitFlag;
import de.rrze.jpwgen.flags.impl.SingleSymbolFlag;
import de.rrze.jpwgen.flags.impl.StartsNoDigitFlag;
import de.rrze.jpwgen.flags.impl.StartsNoLowerCaseFlag;
import de.rrze.jpwgen.flags.impl.StartsNoSymbolFlag;
import de.rrze.jpwgen.flags.impl.StartsNoUpperCaseFlag;

public interface IPwFlag extends Serializable {

	public static final IPwFlag PW_CAPITALS_FLAG = new PwCapitalizeFlag();
	public static final IPwFlag PW_DIGITS_FLAG = new PwDigitsFlag();
	public static final IPwFlag PW_SYMBOLS_FLAG = new PwSymbolsFlag();
	public static final IPwFlag PW_REDUCED_SYMBOLS_FLAG = new PwReducedSymbolsFlag();
	public static final IPwFlag PW_AMBIGOUS_FLAG = new PwAmbiguousFlag();
	public static final IPwFlag STARTS_NO_LOWERCASE_FLAG = new StartsNoLowerCaseFlag();
	public static final IPwFlag ENDS_NO_LOWERCASE_FLAG = new EndsNoLowerCaseFlag();
	public static final IPwFlag STARTS_NO_UPPERCASE_FLAG = new StartsNoUpperCaseFlag();
	public static final IPwFlag ENDS_NO_UPPERCASE_FLAG = new EndsNoUpperCaseFlag();
	public static final IPwFlag STARTS_NO_DIGIT_FLAG = new StartsNoDigitFlag();
	public static final IPwFlag ENDS_NO_DIGIT_FLAG = new EndsNoDigitFlag();
	public static final IPwFlag STARTS_NO_SYMBOL_FLAG = new StartsNoSymbolFlag();
	public static final IPwFlag ENDS_NO_SYMBOL_FLAG = new EndsNoSymbolFlag();
	public static final IPwFlag SINGLE_CAPITAL_FLAG = new SingleCapitalFlag();
	public static final IPwFlag SINGLE_SYMBOL_FLAG = new SingleSymbolFlag();
	public static final IPwFlag AT_LEAST_TWO_SYMBOLS_FLAG = new AtLeast2SymbolsFlag();
	public static final IPwFlag SINGLE_DIGIT_FLAG = new SingleDigitFlag();
	public static final IPwFlag AT_LEAST_TWO_DIGITS_FLAG = new AtLeast2DigitsFlag();
	public static final IPwFlag CONSEC_CAPITALS_FLAG = new ConsecCapitalsFlag();
	public static final IPwFlag CONSEC_DIGITS_FLAG = new ConsecDigitsFlag();
	public static final IPwFlag CONSEC_SYMBOLS_FLAG = new ConsecSymbolsFlag();

	public Long mask(Long flags);

	public Long unmask(Long flags);

	public Long getMask();

	public boolean isMasked(Long flags);

	public String getId();
}
