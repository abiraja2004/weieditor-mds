package com.weieditor.mds;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import com.weieditor.mds.model.Article;
import com.weieditor.mds.model.Document;
import com.weieditor.mds.model.Paragraph;
import com.weieditor.mds.model.Sentence;

public class DocumentBuilderTest {

	@Test
	public void should_parse_article() {
		DocumentBuilder builder = new DocumentBuilder();
		ArticleBuilder articleBuilder = new ArticleBuilder();
		Article article = articleBuilder
				.withTitle("����ί��Ա�ƴ�4�����ļ������Ͳ�����ʾ������")
				.withContent(
						"9��1������11�㣬�»���������Ϣ�ƣ��������ί��Ϥ������ί���Ρ���ί����ǽ�������������Υ�ͣ�Ŀǰ��������֯���顣��������ί������վ�������漰������Ϣȫ��������")
				.build();

		Document doc = builder.parse(article);

		List<Paragraph> paragraphs = doc.getParagraphs();
		assertThat(paragraphs.size(), equalTo(1));
		List<Sentence> sentences = paragraphs.get(0).getSentences();
		assertThat(
				sentences.get(0).getContent(),
				equalTo("9��1������11�㣬�»���������Ϣ�ƣ��������ί��Ϥ������ί���Ρ���ί����ǽ�������������Υ�ͣ�Ŀǰ��������֯���顣"));
		assertThat(sentences.get(1).getContent(),
				equalTo("��������ί������վ�������漰������Ϣȫ��������"));
	}

}
