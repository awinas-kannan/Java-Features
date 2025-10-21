package com.awinas.learning.coreconcepts.innerclass;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XmlUtil {

	// private static Log4Debug logger =
	// Log4DebugFactory.getLog4Debug(XmlUtil.class);
	

	private static class JaxbContextCache {

		private static JaxbContextCache instance = new JaxbContextCache();
		private static Map<Class, JAXBContext> jaxbContextCacheMap;

		private static JaxbContextCache getInstance() {
			return instance;
		}

		private JaxbContextCache() {
			jaxbContextCacheMap = new Hashtable<Class, JAXBContext>();
		}

		public JAXBContext getContext(final Class clazz) throws JAXBException {
			JAXBContext context = jaxbContextCacheMap.get(clazz);
			if (context == null) {
				// logger.debug("Creating JAXB context for ", clazz.getSimpleName());
				context = JAXBContext.newInstance(clazz);
				jaxbContextCacheMap.put(clazz, context);
			}
			return context;
		}

	}

	/**
	 * Method marshal.
	 *
	 * @param object Object
	 * @param cls    Class
	 *
	 *
	 */
	public static String marshal(Object object, Class cls) {
		// throws ArchivioException {

		String xml = "";
		try {
			final JAXBContext jc = JaxbContextCache.getInstance().getContext(cls);
			final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			final Marshaller m = jc.createMarshaller();
			m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(object, byteArrayOutputStream);
			xml = byteArrayOutputStream.toString();
		} catch (Exception e) {
			// logger.severe(e.getMessage());
			// logger.severeStackTrace(e);
			// throw new ArchivioException("Errore nella conversione del xml", e);
		}
		return xml;

	}

	public static String getCollectionXml(List<? extends XMLMarshaller> list, String rootTagName) {
		final StringBuilder xml = new StringBuilder();
		xml.append("<").append(rootTagName).append(">");
		for (XMLMarshaller obj : list) {
			xml.append(obj.toXml());
		}
		xml.append("</").append(rootTagName).append(">");
		return xml.toString();
	}

}
