package com.shinemo.mpns.client.common;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 分页算法封装。 分页须设置: TotalItem（总条数）,缺省为0, 应该在dao中被设置 PageSize（每页条数），应在web层被设置 QueryBase 缺省为20，子类可以通过覆盖
 * getDefaultPageSize() 修改 CurrentPage（当前页）,缺省为1，首页， 应在web层被设置 分页后，可以得到：TotalPage（总页数）
 * FristItem(当前页开始记录位置，从1开始记数) PageLastItem(当前页最后记录位置)  页面上，每页显示条数名字应为： lines ，当前页名字应为： page
 * 
 * 同时加入渲染链接功能，子类覆盖getParameteres方法，返回有效的参数。
 */
public class QueryBase implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long    serialVersionUID = 3258128059449226041L;
    private static final Integer defaultPageSize = new Integer(20);
    private static final Integer defaultFristPage = new Integer(1);
    private static final Integer defaultTotleItem = new Integer(0);
    private Integer              totalItem;
    private Integer              pageSize;
    private Integer              currentPage;

    // for paging
    private int     startRow;
    private int     endRow;
    
    private Map tempParam;
    private Object removeObject;
    
    //for ajax
    private String ajaxPrefix;
    private String ajaxSuffix;
    
    private String charset;
    
    private String from;
    
    private boolean escape = true;
    
    private boolean jsEscape = false;

    private boolean queryTotal = false;
    /**
     * @return Returns the defaultPageSize.
     */
    protected Integer getDefaultPageSize() {
        return defaultPageSize;
    }

    public boolean isFirstPage() {
        return this.getCurrentPage().intValue() == 1;
    }

    public int getPreviousPage() {
        int back = this.getCurrentPage().intValue() - 1;

        if (back <= 0) {
            back = 1;
        }

        return back;
    }

    public boolean isLastPage() {
        return this.getTotalPage() == this.getCurrentPage().intValue();
    }

    public int getNextPage() {
        int back = this.getCurrentPage().intValue() + 1;

        if (back > this.getTotalPage()) {
            back = this.getTotalPage();
        }

        return back;
    }

    /**
     * @return Returns the currentPage.
     */
    public Integer getCurrentPage() {
        if (currentPage == null) {
            return defaultFristPage;
        }

        return currentPage;
    }

    public void setCurrentPageString(String pageString) {
        if (isBlankString(pageString)) {
            this.setCurrentPage(defaultFristPage);
        }

        try {
            Integer integer = new Integer(pageString);

            this.setCurrentPage(integer);
        } catch (NumberFormatException ignore) {
            this.setCurrentPage(defaultFristPage);
        }
    }

    /**
     * @param currentPage The currentPage to set.
     */
    public void setCurrentPage(Integer cPage) {
        if ((cPage == null) || (cPage <= 0)) {
            this.currentPage = null;
        } else {
            this.currentPage = cPage;
        }
        setStartEndRow();
    }
    
    private void setStartEndRow() {
        this.startRow = this.getPageSize() * (this.getCurrentPage() - 1);
        this.endRow = this.startRow + this.getPageSize() - 1;
    }

    /**
     * @return Returns the pageSize.
     */
    public Integer getPageSize() {
        if (pageSize == null) {
            return getDefaultPageSize();
        }

        return pageSize;
    }

    public boolean hasSetPageSize() {
        return pageSize != null;
    }

    public void setPageSizeString(String pageSizeString) {
        if (isBlankString(pageSizeString)) {
            return;
        }

        try {
            Integer integer = new Integer(pageSizeString);

            this.setPageSize(integer);
        } catch (NumberFormatException ignore) {
        }
    }

    /**
     * @param pageSizeString
     *
     * @return
     */
    private boolean isBlankString(String pageSizeString) {
        if (pageSizeString == null) {
            return true;
        }

        if (pageSizeString.trim().length() == 0) {
            return true;
        }

        return false;
    }

    /**
     * @param pageSize The pageSize to set.
     */
    public void setPageSize(Integer pSize) {
        if ((pSize == null) || (pSize.intValue() <= 0)) {
            this.pageSize = null;
        } else {
            this.pageSize = pSize;
        }
        setStartEndRow();
    }

    /**
     * @return Returns the totalItem.
     */
    public Integer getTotalItem() {
        if (totalItem == null) {
            //throw new IllegalStateException("Please set the TotalItem
            // frist.");
            return defaultTotleItem;
        }

        return totalItem;
    }

    /**
     * @param totalItem The totalItem to set.
     */
    public void setTotalItem(Integer tItem) {
        if (tItem == null) {
            throw new IllegalArgumentException("TotalItem can't be null.");
        }

        this.totalItem = tItem;

        int current  = this.getCurrentPage().intValue();
        int lastPage = this.getTotalPage();

        if (current > lastPage) {
            this.setCurrentPage(new Integer(lastPage));
        }
    }

    public int getTotalPage() {
        int pgSize = this.getPageSize().intValue();
        int total  = this.getTotalItem().intValue();
        int result = total / pgSize;

        if ((total == 0) || ((total % pgSize) != 0)) {
            result++;
        }

        return result;
    }

    /**
     * Frist是first写错了
     * 历史原因，不改了
     * @return
     */
    public int getPageFristItem() {
        int cPage = this.getCurrentPage().intValue();

        if (cPage == 1) {
            return 1; // 第一页开始当然是第 1 条记录
        }

        cPage--;

        int pgSize = this.getPageSize().intValue();

        return (pgSize * cPage) + 1;
    }

    public int getPageLastItem() {
        int cPage      = this.getCurrentPage().intValue();
        int pgSize     = this.getPageSize().intValue();
        int assumeLast = pgSize * cPage;
        int totalItem  = getTotalItem().intValue();

        if (assumeLast > totalItem) {
            return totalItem;
        } else {
            return assumeLast;
        }
    }

    /**
     * @return Returns the endRow.
     */
    public int getEndRow() {
        return endRow;
    }

    /**
     * @param endRow The endRow to set.
     */
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    /**
     * @return Returns the startRow.
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * @param startRow The startRow to set.
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    protected String getSQLBlurValue(String value) {
        if (value == null) {
            return null;
        }

        return value + '%';
    }


    protected String formatDate(String datestring) {
        if ((datestring == null) || datestring.equals("")) {
            return null;
        } else {
            return (datestring + " 00:00:00");
        }
    }

    /**
     * 时间查询时,结束时间的 23:59:59
     */
    protected String addDateEndPostfix(String datestring) {
        if ((datestring == null) || datestring.equals("")) {
            return null;
        }

        return datestring + " 23:59:59";
    }

    /**
     * 时间查询时，开始时间的 00:00:00
     */
    protected String addDateStartPostfix(String datestring) {
        if ((datestring == null) || datestring.equals("")) {
            return null;
        }

        return datestring + " 00:00:00";
    }
    
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
    
    public String[] getParameteres() {
    	return new String[0];
    }
    
    public List getTaobaoSlider() {
    	List l = new ArrayList(10);
    	int leftStart = 1;
    	int leftEnd = 2;
    	int mStart = this.getCurrentPage().intValue() - 2;
    	int mEnd = this.getCurrentPage().intValue() + 2;
    	int rStart = this.getTotalPage() - 1;
    	int rEnd = this.getTotalPage();
    	if (mStart <= leftEnd) {
    		leftStart = 0;
    		leftEnd = 0;
    		mStart = 1;
    	}
    	if (mEnd >= rStart) {
    		rStart = 0;
    		rEnd = 0;
    		mEnd = this.getTotalPage();
    	}
    	if (leftEnd > leftStart) {
    		for (int i = leftStart; i <= leftEnd; ++i) {
    			l.add(String.valueOf(i));
    		}
    		l.add("...");
    	}
    	for (int i = mStart; i <= mEnd; ++i) {
    		l.add(String.valueOf(i));
    	}
    	if (rEnd > rStart) {
    		l.add("...");
    		for (int i = rStart; i <= rEnd; ++i) {
    			l.add(String.valueOf(i));
    		}
    	}
    	return l;
    }

	/**
	 * @return Returns the ajaxPrefix.
	 */
	public String getAjaxPrefix() {
		return ajaxPrefix;
	}

	/**
	 * @param ajaxPrefix The ajaxPrefix to set.
	 */
	public QueryBase setAjaxPrefix(String ajaxPrefix) {
		this.ajaxPrefix = ajaxPrefix;
		return this;
	}

	/**
	 * @return Returns the ajaxSuffix.
	 */
	public String getAjaxSuffix() {
		return ajaxSuffix;
	}

	/**
	 * @param ajaxSuffix The ajaxSuffix to set.
	 */
	public QueryBase setAjaxSuffix(String ajaxSuffix) {
		this.ajaxSuffix = ajaxSuffix;
		return this;
	}

	/**
	 * @return Returns the charset.
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @param charset The charset to set.
	 */
	public QueryBase setCharset(String charset) {
		this.charset = charset;
		return this;
	}
	
	/**
	 * 移除一个参数
	 * @param key
	 * @return
	 */
	public QueryBase remove(Object key) {
		if (null == this.removeObject) this.removeObject = new Object();
		replace(key, this.removeObject);
		return this;
	}
	
	/**
	 * 临时修改参数的值
	 * @param key
	 * @param val
	 * @return
	 */
	public QueryBase replace(Object key, Object val) {
		if (null != key && null != val) {
			if (null == this.tempParam) this.tempParam = new HashMap(5);
			this.tempParam.put(String.valueOf(key), val);
		}
		return this;
	}
		
	/**
	 * 解析时间
	 * @param dateTime
	 * @param format
	 * @param def
	 * @return
	 */
    public static final Date parseDate(String dateTime, String format, Date def) {
    	Date date = def;
        try {
        	DateFormat formatter = new SimpleDateFormat(format);
        	date = formatter.parse(dateTime);
        } catch (Exception e) {
            DateFormat f = DateFormat.getDateInstance();
            try {
            	date = f.parse(dateTime);
            }catch (Exception ee) {
            	//ignore
            }
        }
        return date;
    }

	/**
	 * @return Returns the from.
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from The from to set.
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return Returns the escape.
	 */
	public boolean isEscape() {
		return escape;
	}

	/**
	 * @param escape The escape to set.
	 */
	public QueryBase setEscape(boolean escape) {
		this.escape = escape;
		return this;
	}

	/**
	 * @return Returns the jsEscape.
	 */
	public final boolean isJsEscape() {
		return this.jsEscape;
	}

	/**
	 * @param jsEscape The jsEscape to set.
	 */
	public final QueryBase setJsEscape(boolean jsEscape) {
		this.jsEscape = jsEscape;
		return this;
	}
	
    /**
     * 根据javascript中的escape方法进行编码
     * @param str
     * @return 编码后的字符串
     */
    public static final String jsEncode(String str) {
        if (null == str) {
            return null;
        }

        char[] cs = str.toCharArray();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < cs.length; ++i) {
            int c = cs[i] & 0xFFFF;

            if (((c >= '0') && (c <= '9')) ||
                    (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')))) {
                sb.append(cs[i]);
            } else {
                sb.append('%');

                if (c > 255) {
                    sb.append('u');
                }

                sb.append(Integer.toHexString(c));
            }
        }

        return sb.toString();
    }

    /**
     * 解析使用javascript的escape进行编码的字符串，等同于javascript的unescape
     * @param str
     * @return 解析后的字符串
     */
    public static final String jsDecode(String str) {
        if (null == str) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        char[] cs = str.toCharArray();

        for (int i = 0; i < cs.length; ++i) {
            int c = cs[i] & 0xFFFF;

            if (c == '%') {
                if (cs[i + 1] == 'u') {
                    if ((i + 6) > cs.length) {
                        sb.append(cs[i]);
                    } else {
                        try {
                            char cc = (char) Integer.parseInt(new String(cs,
                                        i + 2, 4), 16);
                            sb.append(cc);
                            i += 5;
                        } catch (Exception e) {
                            sb.append(cs[i]);
                        }
                    }
                } else {
                    if ((i + 3) > cs.length) {
                        sb.append(cs[i]);
                    } else {
                        try {
                            char cc = (char) Integer.parseInt(new String(cs,
                                        i + 1, 2), 16);
                            sb.append(cc);
                            i += 2;
                        } catch (Exception e) {
                            sb.append(cs[i]);
                        }
                    }
                }
            } else {
                sb.append(cs[i]);
            }
        }

        return sb.toString();
    }
    
    /**
     * 判断是否有下一页, 并且设置当前页码为下一页
     * @return boolean
     */
    public boolean nextPage() {
         if(this.currentPage != null && this.currentPage.intValue() >= this.getTotalPage()) return false;
         if(this.currentPage == null) {
             this.setCurrentPage(defaultFristPage);
         } else {
             this.setCurrentPage(getNextPage());
         }
         return true;
     }

	
    public boolean isQueryTotal() {
    	return queryTotal;
    }

	
    public void setQueryTotal(boolean queryTotal) {
    	this.queryTotal = queryTotal;
    }
}


