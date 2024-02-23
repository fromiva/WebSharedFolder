package ru.fromiva.wsf.folder;

/**
 * Utility class to transfer information about breadcrumb navigation elements.
 * @param title breadcrumb item name
 * @param url breadcrumb target domain-name relative URL
 */
public record FolderBreadcrumbDto(String title, String url) { }
