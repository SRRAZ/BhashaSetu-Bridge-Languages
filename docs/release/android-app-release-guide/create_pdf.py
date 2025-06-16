#!/usr/bin/env python3

import markdown
import os
from weasyprint import HTML, CSS
from weasyprint.text.fonts import FontConfiguration
import tempfile
import re

def create_pdf_from_markdown():
    # Define the order of markdown files to be included in the PDF
    markdown_files = [
        "01_introduction.md",
        "02_development_environment_setup.md",
        "03_app_configuration_for_release.md",
        "04_keystore_creation.md",
        "05_apk_bundle_generation.md",
        "06_testing_verification.md",
        "07_google_play_submission.md",
        "08_appendix.md"
    ]
    
    # Base directory for markdown files
    base_dir = os.path.dirname(os.path.abspath(__file__))
    
    # Combined HTML content
    combined_html = """
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>English-Hindi Learning App Release Guide</title>
        <style>
            @page {
                size: letter;
                margin: 2cm;
                @top-center {
                    content: "English-Hindi Learning App Release Guide";
                    font-size: 9pt;
                    color: #666666;
                }
                @bottom-center {
                    content: "Page " counter(page) " of " counter(pages);
                    font-size: 9pt;
                    color: #666666;
                }
            }
            body {
                font-family: 'Arial', sans-serif;
                line-height: 1.6;
                color: #333333;
            }
            h1 {
                color: #0066cc;
                font-size: 28pt;
                margin-top: 20pt;
                margin-bottom: 10pt;
                page-break-before: always;
                border-bottom: 1pt solid #0066cc;
                padding-bottom: 5pt;
            }
            h1:first-of-type {
                page-break-before: avoid;
            }
            h2 {
                color: #0066cc;
                font-size: 20pt;
                margin-top: 16pt;
                margin-bottom: 8pt;
            }
            h3 {
                color: #333333;
                font-size: 16pt;
                margin-top: 12pt;
                margin-bottom: 6pt;
            }
            p {
                margin-top: 0;
                margin-bottom: 10pt;
            }
            pre {
                background-color: #f5f5f5;
                padding: 10pt;
                border-radius: 5pt;
                overflow-x: auto;
                font-family: 'Courier New', monospace;
                font-size: 10pt;
                line-height: 1.4;
            }
            code {
                font-family: 'Courier New', monospace;
                background-color: #f5f5f5;
                padding: 2pt 4pt;
                border-radius: 3pt;
                font-size: 10pt;
            }
            table {
                border-collapse: collapse;
                width: 100%;
                margin-top: 10pt;
                margin-bottom: 10pt;
            }
            th, td {
                border: 1pt solid #dddddd;
                padding: 8pt;
                text-align: left;
            }
            th {
                background-color: #f5f5f5;
                font-weight: bold;
            }
            img {
                max-width: 100%;
                height: auto;
                display: block;
                margin: 10pt auto;
                border: 1pt solid #dddddd;
            }
            ul, ol {
                margin-top: 0;
                margin-bottom: 10pt;
            }
            a {
                color: #0066cc;
                text-decoration: none;
            }
            .cover-page {
                text-align: center;
                height: 100vh;
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
            }
            .cover-title {
                font-size: 36pt;
                color: #0066cc;
                margin-bottom: 20pt;
            }
            .cover-subtitle {
                font-size: 18pt;
                color: #666666;
                margin-bottom: 40pt;
            }
            .cover-date {
                font-size: 14pt;
                color: #666666;
            }
            .toc-heading {
                font-size: 24pt;
                color: #0066cc;
                margin-top: 20pt;
                margin-bottom: 20pt;
                text-align: center;
            }
            #toc {
                page-break-before: always;
            }
            .page-break {
                page-break-before: always;
            }
        </style>
    </head>
    <body>
        <div class="cover-page">
            <h1 class="cover-title">English-Hindi Learning App Release Guide</h1>
            <p class="cover-subtitle">A Comprehensive Guide for Android App Publication</p>
            <p class="cover-date">May 2025</p>
        </div>
        
        <div id="toc" class="page-break">
            <h1 class="toc-heading">Table of Contents</h1>
            <ul>
    """
    
    # Generate table of contents
    toc_items = []
    for idx, md_file in enumerate(markdown_files, 1):
        md_path = os.path.join(base_dir, md_file)
        if os.path.exists(md_path):
            with open(md_path, 'r') as file:
                content = file.read()
                # Extract the title (assuming it's the first h1)
                title_match = re.search(r'^# (.+)$', content, re.MULTILINE)
                if title_match:
                    title = title_match.group(1)
                    toc_items.append(f'<li><a href="#{md_file.replace(".md", "")}">{title}</a></li>')
    
    combined_html += "\n".join(toc_items)
    combined_html += """
            </ul>
        </div>
    """
    
    # Process each markdown file
    for idx, md_file in enumerate(markdown_files, 1):
        md_path = os.path.join(base_dir, md_file)
        if os.path.exists(md_path):
            with open(md_path, 'r') as file:
                content = file.read()
                
                # Replace image markdown with HTML that includes proper paths
                content = re.sub(
                    r'!\[(.*?)\]\((.*?)\)',
                    lambda m: f'<img src="{os.path.join(base_dir, "images", os.path.basename(m.group(2)))}" alt="{m.group(1)}">',
                    content
                )
                
                # Convert Markdown to HTML
                html = markdown.markdown(
                    content,
                    extensions=['extra', 'codehilite', 'tables']
                )
                
                # Add section ID for TOC linking
                section_id = md_file.replace(".md", "")
                section_html = f'<div id="{section_id}" class="{"" if idx == 1 else "page-break"}">{html}</div>'
                combined_html += section_html
    
    combined_html += """
    </body>
    </html>
    """
    
    # Create temporary HTML file
    with tempfile.NamedTemporaryFile(suffix='.html', delete=False) as temp_html:
        temp_html.write(combined_html.encode('utf-8'))
        temp_html_path = temp_html.name
    
    # Configure fonts
    font_config = FontConfiguration()
    
    # Create PDF from HTML
    pdf_path = os.path.join(base_dir, 'pdf', 'English-Hindi-Learning-App-Release-Guide.pdf')
    html = HTML(filename=temp_html_path)
    html.write_pdf(
        pdf_path, 
        font_config=font_config,
        presentational_hints=True
    )
    
    # Clean up temporary file
    os.unlink(temp_html_path)
    
    print(f"PDF created successfully: {pdf_path}")
    return pdf_path

if __name__ == "__main__":
    create_pdf_from_markdown()