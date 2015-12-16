package fr.openwide.core.basicapp.core.security.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.passay.CharacterRule;
import org.passay.DictionaryRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalCharacterRule;
import org.passay.IllegalRegexRule;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.Rule;
import org.passay.UsernameRule;
import org.passay.WhitespaceRule;
import org.passay.dictionary.ArrayWordList;
import org.passay.dictionary.WordListDictionary;

import fr.openwide.core.basicapp.core.business.user.model.User;

public class SecurityPasswordRules implements Serializable {

	private static final long serialVersionUID = -2309617143631151956L;

	private Set<Rule> rules = new HashSet<Rule>();

	public static final SecurityPasswordRules DEFAULT = new SecurityPasswordRules()
			.minMaxLength(User.MIN_PASSWORD_LENGTH, User.MAX_PASSWORD_LENGTH);

	public SecurityPasswordRules minLength(int min) {
		rules.add(new LengthRule(min, Integer.MAX_VALUE));
		return this;
	}

	public SecurityPasswordRules maxLength(int max) {
		rules.add(new LengthRule(0, max));
		return this;
	}

	public SecurityPasswordRules minMaxLength(int min, int max) {
		rules.add(new LengthRule(min, max));
		return this;
	}

	public SecurityPasswordRules mandatoryDigits(int min) {
		rules.add(new CharacterRule(EnglishCharacterData.Digit, min));
		return this;
	}

	public SecurityPasswordRules mandatoryDigits() {
		rules.add(new CharacterRule(EnglishCharacterData.Digit));
		return this;
	}

	public SecurityPasswordRules mandatoryNonAlphanumericCharacters() {
		rules.add(new CharacterRule(EnglishCharacterData.Special));
		return this;
	}

	public SecurityPasswordRules mandatoryNonAlphanumericCharacters(int min) {
		rules.add(new CharacterRule(EnglishCharacterData.Special, min));
		return this;
	}

	public SecurityPasswordRules mandatoryUpperCase() {
		rules.add(new CharacterRule(EnglishCharacterData.UpperCase));
		return this;
	}

	public SecurityPasswordRules mandatoryUpperCase(int minUpperCase) {
		rules.add(new CharacterRule(EnglishCharacterData.UpperCase, minUpperCase));
		return this;
	}

	public SecurityPasswordRules mandatoryLowerCase() {
		rules.add(new CharacterRule(EnglishCharacterData.LowerCase));
		return this;
	}

	public SecurityPasswordRules mandatoryLowerCase(int minLowerCase) {
		rules.add(new CharacterRule(EnglishCharacterData.LowerCase, minLowerCase));
		return this;
	}

	public SecurityPasswordRules mandatoryUpperLowerCase() {
		rules.add(new CharacterRule(EnglishCharacterData.LowerCase));
		rules.add(new CharacterRule(EnglishCharacterData.UpperCase));
		return this;
	}

	public SecurityPasswordRules mandatoryUpperLowerCase(int minUpperCase, int minLowerCase) {
		rules.add(new CharacterRule(EnglishCharacterData.UpperCase, minUpperCase));
		rules.add(new CharacterRule(EnglishCharacterData.LowerCase, minLowerCase));
		return this;
	}

	public SecurityPasswordRules forbiddenWhiteSpace() {
		rules.add(new WhitespaceRule());
		return this;
	}

	public SecurityPasswordRules forbiddenRegex(String regex) {
		rules.add(new IllegalRegexRule(regex));
		return this;
	}

	public SecurityPasswordRules forbiddenOrderedNumericalSequence(int sequenceMaxLength, boolean wrap) {
		rules.add(new IllegalSequenceRule(EnglishSequenceData.Numerical, sequenceMaxLength, wrap));
		return this;
	}

	public SecurityPasswordRules forbiddenCharacters(String characters) {
		rules.add(new IllegalCharacterRule(characters.toCharArray()));
		return this;
	}

	public SecurityPasswordRules forbiddenUsername() {
		rules.add(new UsernameRule());
		return this;
	}

	public SecurityPasswordRules forbiddenUsername(boolean matchBackwards, boolean caseInsensitive) {
		rules.add(new UsernameRule(matchBackwards, caseInsensitive));
		return this;
	}

	public SecurityPasswordRules forbiddenPasswords(List<String> forbiddenPasswords) {
		return forbiddenPasswords(forbiddenPasswords, true);
	}

	public SecurityPasswordRules forbiddenPasswords(List<String> forbiddenPasswords, boolean caseInsensitive) {
		if (forbiddenPasswords == null || forbiddenPasswords.isEmpty()) {
			return this;
		}
		rules.add(new DictionaryRule(new WordListDictionary(new ArrayWordList(forbiddenPasswords.toArray(new String[forbiddenPasswords.size()]), !caseInsensitive))));
		return this;
	}

	public Set<Rule> getRules() {
		return rules;
	}

}