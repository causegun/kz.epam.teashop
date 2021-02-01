package dao;

import entity.Language;

public interface LanguageDao extends BaseDao <Long, Language> {
    Language getByName(String languageName);
}
