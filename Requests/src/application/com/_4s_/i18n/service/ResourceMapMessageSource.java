/*
 * Created on Mar 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com._4s_.i18n.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.context.HierarchicalMessageSource;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;

import com._4s_.i18n.dao.MessageDAO;
import com._4s_.i18n.model.MyLocale;
import com._4s_.security.service.UsersMap;

/**
 * @author mragab
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ResourceMapMessageSource extends AbstractMessageSource  
{

	// cachedResourceMaps --> locale & (resource map (key & msg))
	private final Map cachedResourceMaps = new HashMap();

	private LocaleSource localeSource = null;

	private LocaleSource defaultLocaleSource = null;

	private MessageDAO messageDAO = null;

	private String defaultCode = "";

	private String defaultCountry = "";

	private String defaultVariant = "";

	// userMap --> map of users
	private UsersMap userMap = null;

	public UsersMap getUserMap() {
		return userMap;
	}
	public void setUserMap(UsersMap userMap) {
		this.userMap = userMap;
	}

	public Map getCachedResourceMaps() {
		return cachedResourceMaps;
	}
	
	public LocaleSource getLocaleSource() {
		return localeSource;
	}
	public void setLocaleSource(LocaleSource localeSource) {
		this.localeSource = localeSource;
	}

	public LocaleSource getDefaultLocaleSource() {
		return defaultLocaleSource;
	}
	public void setDefaultLocaleSource(LocaleSource defaultLocaleSource) {
		this.defaultLocaleSource = defaultLocaleSource;
	}

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}
	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	public void setDefaultCode(String defaultCode) {
		this.defaultCode = defaultCode;
	}

	public void setDefaultCountry(String defaultCountry) {
		this.defaultCountry = defaultCountry;
	}

	public void setDefaultVariant(String defaultVariant) {
		this.defaultVariant = defaultVariant;
	}

	/**
	 * Cache to hold already generated MessageFormats per message code. Note
	 * that this Map contains the actual code Map, keyed with the Locale.
	 * 
	 * @see #getMessageFormat
	 */
	
	//cachedMessageFormats --> (resource map(key&msg) & messagefromatmaps(code&localemap(locale&messageformat)))
	private final Map cachedMessageFormats = new HashMap();

	private MyLocale getMyLocale(Locale locale) 
	{
		logger.debug(">>>>>>>>>>>>>>>>>>>>.......Inside  getMyLocale(Locale locale)");
		MyLocale myLocale = null;
		String username = null;
		
		SecurityContext sc = (SecurityContext) (SecurityContextHolder.getContext());
		logger.debug(">>>>>>>>>>>>>>>> sc "+sc);
		if(sc != null)
		{
//			logger.debug("------------------------------------------username:--- "+ sc.getAuthentication().getName());
			if (sc.getAuthentication() != null)
			{
				username = sc.getAuthentication().getName();
			}
		
		}
		if(userMap.getUsers().get(username) != null )
		{
			logger.debug(">>>>>>>>>>>>>>>>>>Username "+username);
			// !!!!!!!!!!
			myLocale = (MyLocale)(userMap.getUsers().get(username));
			//(MyLocale)(userMap.getUsers().get(username));
			logger.debug(">>>>>>>>>>>>>>>>>>after username "+myLocale);
		}
		
		else
		if (localeSource != null) 
		{
			myLocale = localeSource.getDefaultMyLocale();
			
			if (myLocale == null) 
			{
				myLocale = localeSource.getDefaultMyLocale();
			}
		} 
		else 
		{
			logger.warn("localeSource not defined");
			if (defaultLocaleSource != null) 
			{
				myLocale = defaultLocaleSource.getPreferenceMyLocale();
				if (myLocale == null) 
				{
					myLocale = defaultLocaleSource.getPreferenceMyLocale();
				}
			} 
			else 
			{
				logger.warn("defaultLocaleSource not defined");
			}
		}

		// /**
		// * If all the above failed to get a MyLocale object
		// * Try to use the plain parameters
		// */
		// if (myLocale==null) {
		//			
		// if ((defaultLanguage!=null)&&(defaultLanguage.length()>0)) {
		// myLocale = new
		// MyLocale(defaultLanguage,defaultCountry,defaultVariant);
		// }
		// }
		//
		// /**
		// * If all the above failed to get a MyLocale object
		// * Use the supplied Locale (Based on Browser information)
		// */
		// if (myLocale==null) {
		// myLocale = new
		// MyLocale(locale.getDisplayLanguage(),locale.getCountry(),locale.getVariant());
		// }

		/**
		 * If all this failed Throw an exception
		 */
		if (myLocale == null) 
		{
			logger.error("No valid Locate found");
			// set myLocale to default Egypt
			myLocale = new MyLocale("ar","Egypt","");
			myLocale.getCode();
			logger.debug(">>>>>>>>>>>>>>>>>.myLocale.getCode() "+myLocale.getCode());
		} 
		else 
		{
			logger.debug(">-----------------------------------");
			logger
					.debug("Retrieved MyLocale: [" + myLocale.getLanguage()
							+ ":" + myLocale.getCountry() + ":"
							+ myLocale.getVariant() + "]");
			logger.debug("-----------------------------------<");
		}
		logger.debug("---------------------------------------------------------------");
		logger.debug("----------------------------My Locale------"+ myLocale+"-----------------------");
		logger.debug("---------------------------------------------------------------");
		return myLocale;
	}

	// return the msg of that key "code" for that local
	protected String resolveCodeWithoutArguments(String code, Locale locale) 
	{
		logger.info("Starting CodeWithoutArguments... code:" + code);

		String result = null;
		Map resourceMap = getResourceMap(getMyLocale(locale));
		if (resourceMap != null) 
		{
			result = getStringOrNull(resourceMap, code);
			logger.warn(">>>>>>>>>>>>>>>resource map doesn't equal null"
					+ result + "<<<<<<<<<<<<<<<<<<<");
		}
		logger.debug(">-----------------------------------");
		logger
				.debug("Resolved (WithoutArguments) key :["
						+ code
						+ "], to message :[|||||||||ResolveCodeWithoutArgument========="
						+ result + "=========||||||||||]");
		logger.debug("-----------------------------------<");
		return result;
	}

	// returns the msg format of a key "code" for that locale
	protected MessageFormat resolveCode(String code, Locale locale) 
	{
		System.out.println("*****ResolveCode********");
		System.out.println("... code:<<<<<<<<<<<"+ code);
		System.out.println("... locale:<<<<<<<<<<<"+ locale);
		logger
				.info(">>>>>>>>>>>>>>>>>>>>>>>>>>Starting Code... code:<<<<<<<<<<<"
						+ code);
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>locale:<<<<<<<<<<<" + locale);

		MessageFormat messageFormat = null;
		Map resourceMap = getResourceMap(getMyLocale(locale));
		if (resourceMap != null) 
		{
			messageFormat = getMessageFormat(resourceMap, code, locale);

			logger.info(">>>>>>>>>>>>>>>>>>>>" + messageFormat
					+ "<<<<<<<<<<<<<<<<<<<");
		}
//		logger.debug(">-----------------------------------");
//		logger.debug(">-----------locale===" + locale);
//		logger.debug(">-----------code========" + code);
//		logger.debug("Resolved (MessageFormat) key :[" + code
//				+ "], to message :[ResolveCode>>>>>>-----"
//				+ messageFormat.toString() + "------<<<<<<<<]");
//		logger.debug("-----------------------------------<");
		return messageFormat;
	}

	/**
	 * Return a ResourceBundle for the given code, fetching already generated
	 * MessageFormats from the cache.
	 * 
	 * @param locale
	 *            the Locale to find the ResourceBundle for
	 * @return the resulting ResourceBundle, or null if none found for the given
	 *         Locale
	 */
	protected Map getResourceMap(MyLocale locale) {
		logger.debug(">-----------------------------------------");
		logger.info("Starting getResourceMap... locale:" + locale.getCode()
				+ "," + locale.getCountry() + "," + locale.getVariant());
		Map resourceMap = null;
		//try to get it from cach
		resourceMap = (Map) cachedResourceMaps.get(locale);
		// it is not i the cach
		if (resourceMap == null) 
		{
			/**
			 * Get Resource Bundle from database
			 */
			resourceMap = messageDAO.getResourceMap(locale);
			logger.warn(">>>>>>>>>>>>>>>>>>>> resourceMap " + resourceMap);
			/**
			 * Cache the recieved resource bundle for further use, that implies
			 * that any change in stored messages would require restarting the
			 * application
			 */
			cachedResourceMaps.put(locale, resourceMap);

			if (resourceMap == null) 
			{
				logger.warn("ResourceMap not found for Locale [" + locale
						+ "] - MessageSource: Database");
			}
		}

		/**
		 * Could be null, if no resource bundle could be found for the specified
		 * locale
		 */
		logger.info("Ending getResourceMap... resourceMap:[" + locale + "]"
				+ ((resourceMap == null) ? "Not found" : ">>>>>Found>>>>>>"));
		logger.debug("-----------------------------------------<");
		return resourceMap;
	}

	/**
	 * Return a MessageFormat for the given bundle and code, fetching already
	 * generated MessageFormats from the cache.
	 * 
	 * @param bundle
	 *            the ResourceBundle to work on
	 * @param code
	 *            the message code to retrieve
	 * @param locale
	 *            the Locale to use to build the MessageFormat
	 * @return the resulting MessageFormat, or null if no message defined for
	 *         the given code
	 */
	protected MessageFormat getMessageFormat(Map resourceMap, String code,
			Locale locale) throws MissingResourceException 
	{
		logger.info(">>>>>>>>>>>>>>>>>>>>>>Starting getMessageFormat... code:"
				+ code);
		logger
				.warn(">>>>>>>>>>>>>>>>>>--------------MessageFormat------------------------------------<<<<<<<<<<<<<<<<<<<<<<<<<");
		Map messageFormatsMap = (Map) this.cachedMessageFormats.get(resourceMap);
		Map localeMap = null;
		if (messageFormatsMap != null) 
		{
			localeMap = (Map) messageFormatsMap.get(code);
			if (localeMap != null) 
			{
				MessageFormat result = (MessageFormat) localeMap.get(locale);
				if (result != null) 
				{
					logger.warn(">>>>>>>>>>>>>>>>>>----------------" + result
							+ "-------------<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					return result;

				}
			}
		}

		String msg = getStringOrNull(resourceMap, code);
		if (msg != null) 
		{
			if (messageFormatsMap == null) 
			{
				messageFormatsMap = new HashMap();
				this.cachedMessageFormats.put(resourceMap, messageFormatsMap);
				logger.warn(">>>>>>>>>>msg>>>>>>>>>>>>>>>>>>>" + msg);
			}
			if (localeMap == null) 
			{
				localeMap = new HashMap();
				messageFormatsMap.put(code, localeMap);
			}
			MessageFormat result = createMessageFormat(msg, locale);
			localeMap.put(locale, result);
			return result;

		}
		return null;
	}

//	 returns the msg of that key "code"
	private String getStringOrNull(Map resourceMap, String key) {
		logger.warn(">>>>>>>>>I'm now inside getStringOrNull<<<<<<<<<"
				+ resourceMap.get(key));
		try 
		{

			// return the msg of that key "code"
			return (String) resourceMap.get(key);

		}
		catch (MissingResourceException ex) 
		{
			// assume key not found
			// -> do NOT throw the exception to allow for checking parent
			// message source
			return null;
		}
	}

	/**
	 * Show the configuration of this MessageSource.
	 */
	public String toString() {
		return getClass().getName();// + ": basenames=[" +
		// StringUtils.arrayToCommaDelimitedString(this.basenames)
		// + "]";
	}

	protected void refreshIfNecessary() {
		cachedResourceMaps.clear();
		logger.info(">>>>>>>>>>>>>cachedResourceMaps " + cachedResourceMaps);
	}
}
