package com.weieditor.mds;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import com.weieditor.mds.model.Article;
import com.weieditor.mds.model.Document;
import com.weieditor.mds.model.Paragraph;
import com.weieditor.mds.model.Sentence;
import com.weieditor.mds.model.Title;
import com.weieditor.mds.model.Word;

public class DocumentParserImpl implements DocumentParser {

	private final String TERMINATOR = "。";
	private Segmenter segmenter;

	public DocumentParserImpl(Segmenter segmenter) {
		this.segmenter = segmenter;
	}

	@Override
	public Document parse(Article article) {
		Document doc = new Document();
		Title title = buildTitle(article, doc);
		doc.setTitle(title);

		String content = article.getContent();
		Scanner scanner = new Scanner(content);
		for (int i = 0; scanner.hasNextLine(); i++) {
			String pContent = scanner.nextLine();
			if (StringUtils.isNotBlank(pContent)) {
				Paragraph p = new Paragraph(doc, i + 1);
				p.setContent(pContent.trim());
				String[] sentenceContents = pContent.split(TERMINATOR);
				for (int j = 0; j < sentenceContents.length; j++) {
					Sentence sentence = buildSentence(p, j, sentenceContents[j]);
					p.add(sentence);
				}
				doc.add(p);
			}
		}
		return doc;
	}

	private Title buildTitle(Article article, Document doc) {
		Title title = new Title(doc);
		String titleContent = article.getTitle();
		title.setContent(titleContent);
		List<Word> titleWords = segmenter.segment(titleContent);
		title.setWords(titleWords);
		return title;
	}

	private Sentence buildSentence(Paragraph p, int j, String sentenceContent) {
		sentenceContent += TERMINATOR;
		Sentence sentence = new Sentence(p, j + 1);
		sentence.setContent(sentenceContent);
		List<Word> words = segmenter.segment(sentenceContent);
		sentence.setWords(words);
		return sentence;
	}

}
