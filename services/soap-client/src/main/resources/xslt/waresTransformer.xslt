<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns2="http://www.intership.ttsw.com.pl/soap-wares-map"
                xmlns:ns3="http://www.intership.ttsw.com.pl/soap-wares"
                version="1.0"
                exclude-result-prefixes="ns3">
    <xsl:template match="/">
        <ns2:MapWares>
            <xsl:for-each select="//ns3:Ware">
                <ns2:MapWare>
                    <uniqueId>
                        <xsl:value-of
                                select="concat(current()/ns3:id,' ', current()/ns3:name)"/>
                    </uniqueId>
                    <type>Ware</type>
                    <ns2:Ware>
                        <id>
                            <xsl:value-of select="current()/ns3:id"/>
                        </id>
                        <name>
                            <xsl:value-of select="current()/ns3:name"/>
                        </name>
                        <category>
                            <xsl:value-of select="current()/ns3:category"/>
                        </category>
                        <quantity>
                            <xsl:value-of select="current()/ns3:quantity"/>
                        </quantity>
                    </ns2:Ware>
                </ns2:MapWare>
            </xsl:for-each>
        </ns2:MapWares>
    </xsl:template>
</xsl:stylesheet>