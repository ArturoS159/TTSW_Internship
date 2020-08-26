<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns2="http://www.intership.ttsw.com.pl/product-model"
                xmlns:ns3="http://www.intership.ttsw.com.pl/soap-first-warehouse"
                version="1.0"
                exclude-result-prefixes="ns3">
    <xsl:template match="/">
        <ns2:Products>
            <xsl:for-each select="//ns3:Ware">
                <ns2:Product>
                    <id>
                        <xsl:value-of select="current()/ns3:id"/>
                    </id>
                    <name>
                        <xsl:value-of select="current()/ns3:name"/>
                    </name>
                    <image>
                        <xsl:value-of select="current()/ns3:photo"/>
                    </image>
                    <manufacturer>
                        <xsl:value-of select="current()/ns3:producer"/>
                    </manufacturer>
                    <warehouse>Warehouse1</warehouse>
                    <price>
                        <xsl:value-of select="current()/ns3:price"/>
                    </price>
                    <maxAmount>
                        <xsl:value-of select="current()/ns3:quantity"/>
                    </maxAmount>
                </ns2:Product>
            </xsl:for-each>
        </ns2:Products>
    </xsl:template>
</xsl:stylesheet>